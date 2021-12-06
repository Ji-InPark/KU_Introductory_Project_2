package src;

import java.util.List;

import java.io.File;
import java.util.ArrayList;

public class OptionName implements Option{
    List<File> fileList;
    String option;

    public OptionName(List<File> fileList, String arg)
    {
        this.option=arg;
        this.fileList=fileList;
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

    private boolean checkCondition(String s, String p)
    {
        int lenS = s.length();
        int lenP = p.length();

        while(p.indexOf("**")>-1)
            p = p.replaceAll("**", "*");
        
        boolean[][] dp = new boolean[lenS + 1][lenP + 1];
        for (int j = 1 ; j <= lenP && p.charAt(j - 1) == '*' ; j++)
            dp[0][j] = true;
        dp[0][0] = true;
        for (int i = 1 ; i <= lenS ; i ++) {
            for (int j = 1 ; j <= lenP ; j ++) {
                char charS = s.charAt(i - 1);
                char charP = p.charAt(j - 1);
                
                if (charP!='?'&&charP!='*') {
                    if (charS == charP) dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }
                
                if (charP == '?')  dp[i][j] = dp[i - 1][j - 1];
                
                if (charP == '*') {
                    
                    if (j - 2 < 0) dp[i][j] = true;
                    else {
                        dp[i][j] = dp[i][j] || dp[i][j - 1];
                        for (int m = 1 ; m < i ; m ++) {
                            dp[i][j] = dp[i][j] || dp[m][j - 1];
                            if (dp[i][j]) break;
                        }
                    }
                }
            }
        }
        return dp[lenS][lenP];
    }

    public List<File> analyze()
    {
        ArrayList<File> results= new ArrayList<>();

        int fileCount = this.fileList.size();
        int fileIndex = 0;
        validateOption();   // 검사를 하기에 앞서서 option의 값이 유효한지 체크

        for(fileIndex=0;fileIndex<fileCount;fileIndex++)
        {
            if(checkCondition(this.fileList.get(fileIndex).getName(), this.option))
                results.add(this.fileList.get(fileIndex));
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
