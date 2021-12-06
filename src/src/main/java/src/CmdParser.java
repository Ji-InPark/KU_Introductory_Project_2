package src;


import java.util.*;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class CmdParser {
    private static CmdParser inst = null;

    private CmdParser() {
    }

    public static CmdParser getInstance() {
        if (inst == null)
            inst = new CmdParser();
        return inst;
    }

    public List<File> allFiles = null;

    public CmdExecuter parse(String cmd) throws IllegalArgumentException, IOException {
        List<Option> options = new ArrayList<>();
        String delim = " ";

        StringTokenizer cmdTokenizer = new StringTokenizer(cmd, delim, true);

        int tokenCnt = 0;
        LinkedList<String> splited = new LinkedList<>();
        while (cmdTokenizer.hasMoreTokens()) {
            String currentToken = cmdTokenizer.nextToken();
            splited.offer(currentToken);

            tokenCnt++;
        }

        if (tokenCnt < 3) {
            throw new IllegalArgumentException("올바른 커맨드가 아닙니다 (인수가 너무 적습니다)");
        }

        String rootPath = pollUntilNotDelim(splited, delim);
        if (rootPath.equals("--help")) {
            HelpMsg helpMsg = new HelpMsg();
            helpMsg.printMsg();
            System.exit(0);

            return null;
        }

        //building path is ended
        rootPath = rootPath.replaceAll("\"", "");

        File rootFile = new File(rootPath);
        allFiles = new ArrayList<>(Utils.flatFiles(rootFile));
        if (!rootFile.exists()) {
            throw new IOException("존재하지 않는 경로입니다: " + rootPath);
        }
        if (!rootFile.isDirectory()) {
            throw new IllegalArgumentException("루트 파일 경로에 해당하는 파일은 디렉토리여야 합니다");
        }

        boolean requireOp= false;
        String option = null;
        Stack<String> operatorStrings = new Stack<>();
        splited.removeIf(s -> s.equals(delim));

        splited= splitGroupperFromExpr(splited);

//        System.out.println("splited: " + splited);
        while (splited.peek() != null) {
//            System.out.println("=========디버그========");
//            System.out.println("current: " + splited.peek());
//            System.out.println("operatorStrings: " + operatorStrings);
//            System.out.println("options: " + options);
//            System.out.println("requireOp: " + requireOp);

            if(requireOp){
                //require operator
                String currentOperator = pollUntilNotDelim(splited, delim);
                boolean useDefaultOperator = OperatorUtil.getInstance().getOperator(currentOperator) == null;
                if (useDefaultOperator) {
                    //현재 토큰값이 operator가 아닌 경우 연산자를 and로 취급
                    splited.addFirst(currentOperator);
                    currentOperator = "-and";
                }

                while (!operatorStrings.isEmpty()) {
                    String topOperator = operatorStrings.peek();
                    if (currentOperator.equals(")")) {
                        while (true) {
                            String popedOp= operatorStrings.pop();
                            if(popedOp.equals("(")){
                                break;
                            }else{
                                options.add(OperatorUtil.getInstance().getOperator(popedOp));
                            }
                        }
                        break;
                    }
                    if (topOperator.equals("(") && !currentOperator.equals(")"))
                        break;

                    if (OperatorUtil.getInstance().comparePriority(currentOperator, topOperator) <= 0) {
                        String popedOp = operatorStrings.pop();
//                        System.out.println("pop!"+  popedOp);
                        if (!popedOp.equals("(") && !popedOp.equals(")"))
                            options.add(OperatorUtil.getInstance().getOperator(popedOp));
                    } else {
                        break;
                    }
                }
                if(!currentOperator.equals(")")){
                    operatorStrings.push(currentOperator);
                }
                requireOp= false;

                if(currentOperator.equals("-not")){
                    requireOp= true;
                }
            }else if (option == null) {
                option= pollUntilNotDelim(splited, delim);
                if(OperatorUtil.getInstance().getOperator(option)!= null){
                    splited.addFirst(option);
                    option= null;
                    requireOp= true;
                }
            } else {
                String arg = pollUntilNotDelim(splited, delim);
                switch (option) {
                    case "-size":
                        options.add(new OptionSize(allFiles, arg));
                        break;
                    case "-name":
                        options.add(new OptionName(allFiles, arg));
                        break;
                    case "-type":
                        options.add(new OptionType(allFiles, arg));
                        break;
                    case "-mtime":
                        options.add(new OptionTime(arg, allFiles));
                        break;
                    case "-maxdepth":
                        try {
                            options.add(new OptionMaxdepth(rootFile, Integer.parseInt(arg)));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("--maxdepth의 인자는 0이상의 정수로 주어져야 합니다");
                        }
                        break;
                }
                option = null;
                requireOp= true;
            }
        }
        while (!operatorStrings.isEmpty()) {
            options.add(OperatorUtil.getInstance().getOperator(operatorStrings.pop()));
        }

//        System.out.println("OP parsed");
//        for (Option op : options) {
//            System.out.println(op.getSymbol());
//        }

        CmdExecuter executer= new CmdExecuter(allFiles, options);
        return executer;
    }

    private String pollUntilNotDelim(Queue<String> splited, String delim) {
        if (splited.isEmpty())
            return null;

        while (!splited.isEmpty()) {
            String current = splited.poll();
            if (!current.equals(delim)) {
                return current;
            }
        }

        return null;
    }

    private LinkedList<String> splitGroupperFromExpr(LinkedList<String> expr){
        if(expr.isEmpty()){
           return expr;
        }

        LinkedList<String> result= new LinkedList<>();
        for(int i=0; i< expr.size(); i++){
            String currentExpr= expr.get(i);
            if(!currentExpr.contains("(") && !currentExpr.contains(")")){
                result.add(currentExpr);
            }else{
                //괄호(그루퍼) 처리 필요
                currentExpr= currentExpr.replaceAll("\\(", " ( ");
                currentExpr= currentExpr.replaceAll("\\)", " ) ");

//                System.out.println("괄호처리 완료: "+ currentExpr);

                StringTokenizer tokenizer= new StringTokenizer(currentExpr, " ", false);
                while(tokenizer.hasMoreTokens()){
                    String token= tokenizer.nextToken();
                    result.add(token);
                }
            }
        }

        return result;
    }
}
