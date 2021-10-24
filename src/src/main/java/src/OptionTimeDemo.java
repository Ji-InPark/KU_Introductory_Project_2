package src;

import java.io.File;
import java.util.*;

public class OptionTimeDemo {
    public static void main(String[] args){

        File targetPath= new File("/Users/namgung-geon/Downloads");
        ArrayList fileList= new ArrayList(Arrays.asList(targetPath.listFiles()));

        OptionTime optionTime= new OptionTime("+1020T000000", fileList);
        List<File> resultFiles= optionTime.analyze();
        int cnt= 1;
        for(File file: resultFiles){
//            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            Calendar calendar= Calendar.getInstance();
//            calendar.setTimeInMillis(file.lastModified());
//            Date date= calendar.getTime();

            System.out.println(cnt + "\t"+ file.getName());
            cnt++;
        }
    }
}
