package src;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        CmdParser parser= CmdParser.getInstance();

        try {
            String cmd = "\".\" -not(-maxdepth 1 -name *.csv) -and -type f";
            CmdExecuter executer=  parser.parse(cmd);
            if(executer!= null) executer.exeucte();

        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
