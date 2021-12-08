package src;


public class App {
    public static void main(String[] args) {
        CmdParser parser= CmdParser.getInstance();

        try {
            CmdExecuter executer=  parser.parse(args);
            if(executer!= null) executer.exeucte();

        } catch (Exception e) {
            System.out.println("오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
