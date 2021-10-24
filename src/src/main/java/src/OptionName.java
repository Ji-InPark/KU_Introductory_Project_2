package src;

import java.util.ArrayList;
/*
class find
-------
field
result -> arraylist tree
filelist -> 이것도
-------
constructor
find(list) : filelist=list

method
analyze(String input) : input 분석해서 option 함수 호출
option1()
option2()
option...()
*/  



public class OptionName {
    ArrayList<String> result;
    String[] fileList;
    String option;

    public OptionName(String[] fileList)
    {
        this.fileList=fileList;
    }

    public void setOption(String option)
    {
        this.option=option;
    }
}
