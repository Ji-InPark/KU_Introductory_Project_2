package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppTest {
    private static String tcPath= "C:\\Users\\windowhan\\Desktop\\test-konkuk\\testcase\\";
    public static void main(String[] args){
        if(args.length> 0){
            tcPath= args[0];
        }

        System.out.println("테스트를 시작합니다");
        System.out.println("테스트 경로는 "+ tcPath+ " 입니다");
        System.out.println("\n\n\n");

        //key: command
        //value: expectation value
        HashMap<String, String> tc= new HashMap<>();


        tc.put("-name \"*test.txt\"", "2개");
        tc.put("-name \"t*.txt\"", "2개");
        tc.put("-name \"test*\"", "32개");
        tc.put("-name \"*t*.txt\"", "6개");
        tc.put("-name \"*es*.t*\"", "2개");
        tc.put("-mtime 20200230T101010", "오류발생");
        tc.put("-mtime 20200228T250000", "오류발생");
        tc.put("-mtime 20200228T232500", "오류발생");
        tc.put("-mtime 20200228T230065", "오류발생");
        tc.put("-mtime 20211311T000000", "오류발생");
        tc.put("-mtime 20211233T010101", "오류발생");
        tc.put("-name \"[a,d,3]est.txt\"", "1개");
        tc.put("-name \"[a,p,2]est.txt\"", "2개");
        tc.put("-not (-size +1k -and -type d)", "37개");
        tc.put("-not ((-size -1G -and -type f) -or -name \"[test]*.txt\")", "30개");
        tc.put("((-size -2M -and -size +1k) -and -not -maxdepth 4)", "1개");

        for(String optionExpr: tc.keySet()){
            String cmd= createCmd(optionExpr);
            String expect= tc.get(optionExpr);

            System.out.println("================커맨드: "+ cmd);
            System.out.println("================예상동작: "+ expect);
            exec(cmd);
        }

        System.out.println("================커맨드: "+ "\""+ tcPath+"test6/\" -maxdepth 3");
        System.out.println("================예상동작: 15개");
        exec("\""+ tcPath+"test6/\" -maxdepth 3");
    }

    private static void exec(String cmd){
        /*CmdParser parser= CmdParser.getInstance();

        try {
            CmdExecuter executer=  parser.parse(cmd);
            if(executer!= null) executer.exeucte();

        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\n\n");*/
    }
    private static String createCmd(String optionExpr){
        return String.format("\"%s\" %s", tcPath, optionExpr);
    }
}
