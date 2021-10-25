package src.demo;

import src.FileList;
import src.HelpMsg;
import src.OptionMaxdepth;
import src.OptionType;

import java.util.ArrayList;
import java.io.File;

public class OptionMaxdepthTypeDemo {
    private FileList fileList;
    private ArrayList<File> result;
    public void filter(String path){
        // this method find a certain File class through the input
        // input can be divided into one or more parts
        // [path] [expressions1] [expression2....]
        // we can get path and File Node from this

    }
    public static void printArrayList(ArrayList<String> list){ // 임시로 arraylist 출력하는 함수
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }

    public static void main(String[] args){
        HelpMsg helpMsg = new HelpMsg();
        FileList fileList = new FileList(".");
        // it will be the args[0]

        OptionMaxdepth options1 = new OptionMaxdepth(fileList);
        OptionMaxdepth options2 = new OptionMaxdepth(fileList);
        OptionType options3 = new OptionType(fileList);
        OptionType options4 = new OptionType(fileList);
        System.out.println("test1");
        printArrayList(options1.maxdepth(2));
        System.out.println("test2");
        printArrayList(options2.maxdepth(3));

        /*
        Result is as follows:
            test1
            /
            /1_0
            /1_0/2_0
            /1_0/2_1
            /1_0/2_2
            /1_1
            /1_1/2_3
            /1_1/2_4
            /1_1/2_5
            test2
            ./
            ./2_3
            ./2_4
            ./2_5
            ./2_5/3_1
        */
        System.out.println("\n\n");
        System.out.println("test3");
        printArrayList(options3.type("d"));
        // -> $ ./cfind / -type d (starts from root)
        System.out.println("test4");
        printArrayList(options4.type("f"));
        // -> $ ./cfind . -type f (starts from /1_1)

        /*
        Result is as follows:
            test3
            /
            /1_0
            /1_1
            /1_1/2_5
            /1_1/2_5/3_1
            test4
            ./2_3
            ./2_4
            ./2_5/3_2
        */
    }
}
