package src.demo;

import src.FileList;
import src.OptionSize;
import src.Utils;

public class OptionSizeDemo {
    public static void main(String[] args){
        FileList mFileList= new FileList(".");

        OptionSize optionSize= new OptionSize(mFileList, "-1000B");
        Utils.printResult(optionSize.analyze());
    }
}
