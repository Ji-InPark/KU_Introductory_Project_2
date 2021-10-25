package src;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        try {
            String cmd = "";
            for(int i=0;i<args.length;i++){
                cmd+= args[i]+ " ";
            }

            StringTokenizer cmdTokenizer = new StringTokenizer(cmd, " ", false);

            int tokenCnt = 0;
            Queue<String> splited = new LinkedList<>();
            while (cmdTokenizer.hasMoreTokens()) {
                String currentToken = cmdTokenizer.nextToken();
                splited.offer(currentToken);

                tokenCnt++;
            }

            if (tokenCnt < 2) {
                throw new IllegalArgumentException("올바른 커맨드가 아닙니다 (인수가 너무 적습니다)");
            }

            String cmdName = splited.poll();
            if (!cmdName.equals("cfind"))
                throw new IllegalArgumentException("올바른 커맨드가 아닙니다");

            String rootPath = splited.poll();
            HelpMsg helpMsg = new HelpMsg();
            if (rootPath.equals("--help")) {
                helpMsg.printMsg();
                return;
            }

            File rootFile = new File(rootPath);
            ArrayList<File> allFiles = new ArrayList<>(Utils.flatFiles(rootFile));
            if (!rootFile.exists()) {
                throw new IOException("존재하지 않는 경로입니다");
            }
            if (!rootFile.isDirectory()) {
                throw new IllegalArgumentException("루트 파일 경로에 해당하는 파일은 디렉토리여야 합니다");
            }
            if (splited.peek() == null) {
                Utils.printResult(allFiles);
            }

            List<Option> options = new ArrayList<>();
            String option = null;
            String arg = null;
            while (splited.peek() != null) {
                if (option == null) {
                    option = splited.poll();
                    if(option.equals("-a")){
                        option= null;
                    }
                } else if (arg == null) {
                    arg = splited.poll();
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

            List<File> prevFiles = null;
            for (Option currentOption : options) {
                if (prevFiles == null) {
                    prevFiles = currentOption.analyze();
                } else {
                    List<File> currentFiles = currentOption.analyze();

                    Iterator iterator = prevFiles.iterator();
                    while (iterator.hasNext()) {
                        File file = (File) iterator.next();
                        //set and operation
                        if (!currentFiles.contains(file)) {
                            iterator.remove();
                        }
                    }
                }
            }

            Utils.printResult(prevFiles);
        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
        }
    }
}
