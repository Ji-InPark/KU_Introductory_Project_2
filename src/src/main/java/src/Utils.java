package src;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    private Utils(){}

    public static List<File> flatFiles(File path){
        List<File> files= new ArrayList<>();
        if(path.isDirectory()){
            files.add(path);
            for(File child: path.listFiles()){
                files.addAll(flatFiles(child));
            }
        }else{
            files.add(path);
        }
        return files;
    }
    public static void printResult(List<File> fileList){
        if(fileList.isEmpty()){
            System.out.println("일치하는 파일이 없습니다");
            return;
        }

        int cnt= 1;
        System.out.println("찾은 파일 수: "+ fileList.size());
        for(File file: fileList){
            System.out.println(cnt + "\t"+ file.getPath());

//            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            Calendar calendar= Calendar.getInstance();
//            calendar.setTimeInMillis(file.lastModified());
//            Date date= calendar.getTime();
//            System.out.println("> "+ dateFormat.format(date));

            cnt++;
        }
    }
}
