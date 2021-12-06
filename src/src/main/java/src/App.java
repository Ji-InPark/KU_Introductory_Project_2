package src;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        CmdParser parser= CmdParser.getInstance();

        try {
            String cmd = "\".\" -name Option*";
            List<Option> options= parser.parse(cmd);

            if(options.isEmpty()){
                Utils.printResult(parser.allFiles);
                return;
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
            e.printStackTrace();
        }
    }
}
