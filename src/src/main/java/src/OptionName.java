package src;

import java.util.List;

import java.io.File;
import java.util.ArrayList;

public class OptionName implements Option{
    List<File> fileList;
    String option;
    int[][] dp = new int[999][999];
    String target;

    List<String> tokenList;

    public OptionName(List<File> fileList, String arg)
    {
        this.option=arg;
        this.fileList=fileList;
        this.tokenList = new ArrayList<String>();
        this.replaceToken();
    }


    private void validateOption(){
        // \ / : * ? " < > |
        String[] errorPattern = {"\\*", "\\\\", "\\", "\\\"", ":", "\\?", "<", ">", "|"};
        for(int patternIndex=0;patternIndex<errorPattern.length;patternIndex++)
        {
            if(this.option.indexOf(errorPattern[patternIndex])>-1)
            {
                throw new IllegalArgumentException("올바른 name이 아닙니다.");
            }
        }
    }

    private void replaceToken(){
        boolean extract_flag = false;
        String tmpToken = "";

        char[] pattern_string = this.option.toCharArray();
        for(int i=0;i<pattern_string.length;i++)
        {
            if(((i==0 || pattern_string[i-1]!='\\') && pattern_string[i]=='[') || extract_flag==true)
            {
                extract_flag = true;
                tmpToken += pattern_string[i];
            }

            if(((i==0 || pattern_string[i-1]!='\\') && pattern_string[i]==']') && extract_flag==true)
            {
                extract_flag = false;
                this.tokenList.add(tmpToken);
                //System.out.println("range token : " + tmpToken);
                tmpToken="";
            }
        }

        for(int j=0;j<this.tokenList.size();j++)
        {
            tmpToken = this.tokenList.get(j);
            tmpToken = tmpToken.replace("[", "\\[").replace("]", "\\]");
            //System.out.print("replace target token : " + tmpToken);
            this.option = this.option.replaceAll(tmpToken, "^");
        }

        //System.out.println("가공 후 pattern : " + this.option);
    }

    private int checkCondition(int pattern_index, int string_index, int token_index)
    {
        if(this.option.length() == pattern_index)
        {
            /*System.out.println("coverage 2");
            System.out.println(this.target);
            System.out.println(this.option);
            System.out.println(string_index);
            System.out.println(this.target.length());*/
            if(string_index==this.target.length())
                return 1;
            else
                return 0;
        }

        int ret = dp[pattern_index][string_index];
        int skip = 0;
        if(ret!=-1){
            return ret;
        }

        while(string_index<(this.target.length()) && pattern_index<(this.option.length()) && (this.option.charAt(pattern_index)=='?' || this.target.charAt(string_index)==this.option.charAt(pattern_index))) {
            //System.out.println("Compare 1 : " + this.target.charAt(string_index));
            //System.out.println("Compare 2 : " + this.option.charAt(pattern_index));
            pattern_index++;
            string_index++;
        }

        if(pattern_index==this.option.length() && string_index==this.target.length())
            return 1;

        if(this.option.charAt(pattern_index)=='*')
        {
            //System.out.println("regex whilecard");
            skip = 0;
            while(true){
                if(skip+string_index >= this.target.length()) {
                    return 1;
                }

                if(checkCondition(pattern_index+1, skip+string_index, token_index)==1) {
                    return 1;
                }
                skip+=1;
            }
        }

        if(this.option.charAt(pattern_index)=='^')
        {
            String token = this.tokenList.get(token_index).replace("[", "").replace("]", "");
            int start = (int)token.split(",")[0].charAt(0);
            int end = (int)token.split(",")[1].charAt(0);
            int length = Character.getNumericValue(token.split(",")[2].charAt(0));
            token_index++;

            /*System.out.println("token : " + token);
            System.out.println("start : " + String.valueOf(start));
            System.out.println("end : " + String.valueOf(end));
            System.out.println("length : " + String.valueOf(length));
            */

            for(int k=string_index;k<(string_index+length);k++)
            {
                if(this.target.charAt(k) < start && this.target.charAt(k) > end) {
                    return 0;
                }
            }

            if(checkCondition(pattern_index+1, (string_index+length), token_index)==1) {
                return 1;
            }
        }
        return 0;
    }

    public List<File> analyze()
    {
        ArrayList<File> results= new ArrayList<>();

        int fileCount = this.fileList.size();
        int fileIndex = 0;
        validateOption();   // 검사를 하기에 앞서서 option의 값이 유효한지 체크

        for(fileIndex=0;fileIndex<fileCount;fileIndex++) {
            this.target = this.fileList.get(fileIndex).getName();
            dp = new int[this.option.length()+1][this.fileList.get(fileIndex).getName().length()+1];
            for(int i=0;i<dp.length;i++)
            {
                for(int j=0;j<dp[i].length;j++){
                    dp[i][j] = -1;
                }
            }
            //System.out.println("Target String : " + this.target);
            //System.out.println("Pattern String : " + this.option);
            if (checkCondition(0,0, 0)==1) {
                results.add(this.fileList.get(fileIndex));
            }
        }

        return results;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
    }

    @Override
    public String getSymbol() {
        return "-name";
    }
}
