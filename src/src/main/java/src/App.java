package src;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        CmdParser parser= CmdParser.getInstance();

        try {
            //String cmd = "\"/Users/namgung-geon/java_cmd/testcase/\" -not (-size +1k -and -type d)";
            //cmd= argToCmd(args);

            CmdExecuter executer=  parser.parse(args);
            if(executer!= null) executer.exeucte();

        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String argToCmd(String[] args){
        StringBuilder builder= new StringBuilder();
        for(int i=0; i< args.length; i++){
            builder.append(args[i]+ " ");
        }

        return builder.toString();
    }
}
