package src;


import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

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

    public List<Option> parse(String cmd) throws IllegalArgumentException, IOException {
        List<Option> options = new ArrayList<>();
        String delim= " ";

        StringTokenizer cmdTokenizer = new StringTokenizer(cmd, delim, true);

        int tokenCnt = 0;
        Queue<String> splited = new LinkedList<>();
        while (cmdTokenizer.hasMoreTokens()) {
            String currentToken = cmdTokenizer.nextToken();
            splited.offer(currentToken);

            tokenCnt++;
        }

        if (tokenCnt < 3) {
            throw new IllegalArgumentException("올바른 커맨드가 아닙니다 (인수가 너무 적습니다)");
        }

        String cmdName = pollUntilNotDelim(splited, delim);
        if (!cmdName.equals("cfind"))
            throw new IllegalArgumentException("올바른 커맨드가 아닙니다");

        String rootPath = pollUntilNotDelim(splited, delim);
        if (rootPath.equals("--help")) {
            HelpMsg helpMsg = new HelpMsg();
            helpMsg.printMsg();
            System.exit(0);

            return options;
        }
        int quoteCnt = 0;
        char quote = 0;
        if (rootPath.contains("\"")) {
            quote = '"';
            quoteCnt++;
        } else if (rootPath.contains("\'")) {
            quote = '\'';
            quoteCnt++;
        }
        //find close quote
        while (!splited.isEmpty() && quoteCnt % 2 != 0) {
            String current = splited.poll();
            if(current.equals(delim)){
                rootPath+= delim;
                continue;
            }
            for (char c : current.toCharArray()) {
                if (c == quote) {
                    quoteCnt++;
                }
            }
            rootPath += current;
        }
        //building path is ended
        if(OsUtil.isMac()){
            rootPath= rootPath.replaceAll("\'", "");
            rootPath= rootPath.replaceAll("\"", "");
        }

        File rootFile = new File(rootPath);
        allFiles = new ArrayList<>(Utils.flatFiles(rootFile));
        if (!rootFile.exists()) {
            throw new IOException("존재하지 않는 경로입니다: "+ rootPath);
        }
        if (!rootFile.isDirectory()) {
            throw new IllegalArgumentException("루트 파일 경로에 해당하는 파일은 디렉토리여야 합니다");
        }


        String option = null;
        String arg = null;
        while (splited.peek() != null) {
            if (option == null) {
                option = pollUntilNotDelim(splited, delim);
                if (option.equals("-a")) {
                    option = null;
                }
            } else if (arg == null) {
                arg = pollUntilNotDelim(splited, delim);
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
                arg = null;
            }
        }

        return options;
    }

    private String pollUntilNotDelim(Queue<String> splited, String delim){
        if(splited.isEmpty())
            return null;

        while(!splited.isEmpty()){
            String current= splited.poll();
            if(!current.equals(delim)){
                return current;
            }
        }

        return null;
    }
}
