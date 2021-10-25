package src.demo;

import src.OptionMaxdepth;
import src.Utils;

import java.io.File;

public class OptionMaxdepthTypeDemo {

    public static void main(String[] args){
        File rootFile= new File(".");

        OptionMaxdepth optionMaxdepth= new OptionMaxdepth(rootFile, 0);
        System.out.println("depth 0===========");
        Utils.printResult(optionMaxdepth.analyze());

        optionMaxdepth= new OptionMaxdepth(rootFile, 1);
        System.out.println("depth 1===========");
        Utils.printResult(optionMaxdepth.analyze());
    }
}
