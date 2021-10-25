package src.demo;

import src.FileList;
import src.OptionTime;
import src.Utils;

import java.io.File;
import java.util.*;

public class OptionTimeDemo {
    public static void main(String[] args){
        FileList mFileList= new FileList(".");

        OptionTime optionTime= new OptionTime("+20211001T", mFileList);
        List<File> resultFiles= optionTime.analyze();

        Utils.printResult(resultFiles);
    }
}
