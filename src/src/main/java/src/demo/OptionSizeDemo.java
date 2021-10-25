package src.demo;

import java.util.List;
import src.FileList;
import src.OptionSize;
import src.Utils;

import java.io.File;

public class OptionSizeDemo {
    public static void main(String[] args){
        List<File> fileList= Utils.flatFiles(new File("."));

        OptionSize optionSize= new OptionSize(fileList, "-1000B");
        Utils.printResult(optionSize.analyze());
    }
}
