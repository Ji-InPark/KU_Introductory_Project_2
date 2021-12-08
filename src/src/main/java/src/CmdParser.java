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

    public CmdExecuter parse(String[] args) throws IllegalArgumentException, IOException {
        if(args.length< 1){
            throw new IllegalArgumentException("인자 갯수가 너무 적습니다");
        }

        List<Option> options = new ArrayList<>();
        String delim = " ";

        LinkedList<String> splited= new LinkedList<>();
        for(String arg: args){
            splited.offer(arg);
        }

        String rootPath = pollUntilNotDelim(splited, delim);
        if (rootPath.equals("--help")) {
            HelpMsg helpMsg = new HelpMsg();
            helpMsg.printMsg();
            System.exit(0);

            return null;
        }

        File rootFile = new File(rootPath);
        if (!rootFile.exists()) {
            throw new IOException("존재하지 않는 경로입니다: " + rootPath);
        }
        if (!rootFile.isDirectory()) {
            throw new IllegalArgumentException("루트 파일 경로에 해당하는 파일은 디렉토리여야 합니다");
        }
        allFiles = new ArrayList<>(Utils.flatFiles(rootFile));

        boolean requireOp= false;
        String option = null;
        Stack<String> operatorStrings = new Stack<>();
        splited.removeIf(s -> s.equals(delim));

        splited= splitGroupperFromExpr(splited);

        boolean not= false;
        int groupOpenCnt= 0;

//        System.out.println("splited: " + splited);
        while (splited.peek() != null) {
//            System.out.println("=========디버그========");
//            System.out.println("current: " + splited.peek());
//            System.out.println("operatorStrings: " + operatorStrings);
//            System.out.println("options: " + options);
//            System.out.println("requireOp: " + requireOp);
//            System.out.println("mode NOT: " + not);
//            System.out.println("groupOpenCnt: " + groupOpenCnt);

            if(requireOp){
                //require operator
                String currentOperator = pollUntilNotDelim(splited, delim);
                if(currentOperator.equals("-not")){
                    not= true;
                    String nextOp= pollUntilNotDelim(splited, delim);
                    if(OperatorUtil.getInstance().getOperator(nextOp)== null){
                        requireOp= false;
                    }
                    splited.addFirst(nextOp);
                    continue;
                }
                boolean useDefaultOperator = OperatorUtil.getInstance().getOperator(currentOperator) == null;
                if (useDefaultOperator) {
                    //현재 토큰값이 operator가 아닌 경우 연산자를 and로 취급
                    splited.addFirst(currentOperator);
                    currentOperator = "-and";
                }

                if(not){
                    if(currentOperator.equals("(")){
                        groupOpenCnt++;
                    }else if(currentOperator.equals(")")){
                        groupOpenCnt--;
                    }
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
                    default:
                        throw new IllegalArgumentException("올바르지 않은 옵션입니다");
                }
                option = null;
                requireOp= true;

                if(not && groupOpenCnt== 0){
                    //invert
                    options.add(new OperatorNOT());
                    not= false;
                }
            }
        }
        while (!operatorStrings.isEmpty()) {
            Operator op= OperatorUtil.getInstance().getOperator(operatorStrings.pop());
            if(!op.getSymbol().equals("("))
                options.add(op);
        }
        if(not && groupOpenCnt== 0){
            //invert
            options.add(new OperatorNOT());
            not= false;
        }

        if(option!= null){
            throw new IllegalArgumentException("문법 오류: 값이 주어지지 않은 옵션이 있습니다.");
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
