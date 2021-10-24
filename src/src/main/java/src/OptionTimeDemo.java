package src;

import java.io.File;
import java.util.*;

public class OptionTimeDemo {
    public static void main(String[] args){

        File targetPath= new File("/Users/namgung-geon/Downloads");

        OptionTime optionTime= new OptionTime("+1020T000000", targetPath);
        List<File> resultFiles= optionTime.analyze();

        Utils.printResult(resultFiles);
    }
}
