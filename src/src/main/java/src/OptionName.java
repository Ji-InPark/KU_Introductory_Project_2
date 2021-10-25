package src;

import java.util.ArrayList;

public class OptionName {
    ArrayList<String> result;
    String[] fileList;
    String option;

    public OptionName(String[] fileList)
    {
        this.fileList=fileList;
        this.result = new ArrayList<String>();
    }

    public void setOption(String option)
    {
        this.option=option;
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

    private boolean checkCondition(char[] filename, char[] option)
    {
        int filenameLength = filename.length;
        int optionLength = option.length;

        int filenameIndex = 0;
        int optionIndex = 0;

        while(filenameIndex < (filenameLength-1) && optionIndex < (optionLength-1))
        {
            if(filename[filenameIndex]==option[optionIndex] || option[optionIndex]=='?')
            {
                filenameIndex++;
                optionIndex++;
            }

            else
            {
                if(option[optionIndex]=='*')
                {
                    while((optionIndex+1)<option.length && (option[optionIndex+1]=='*' || option[optionIndex+1]=='?'))
                        optionIndex++;

                    while((optionIndex+1)<option.length && (filenameIndex+1)<filename.length && option[optionIndex+1]!=filename[filenameIndex])
                    {
                        filenameIndex++;
                    }
                    optionIndex++;
                }

                else
                {
                    return false;
                }
            }
        }

        if(optionIndex == (optionLength-1))
        {
            System.out.println(optionIndex);
            System.out.println(filenameIndex);
            System.out.println(new String(filename));
            this.result.add(0, new String(filename));
            return true;
        }

        return false;
    }

    public ArrayList<String> analyze()
    {
        int fileCount = this.fileList.length;
        int fileIndex = 0;
        validateOption();   // 검사를 하기에 앞서서 option의 값이 유효한지 체크

        for(fileIndex=0;fileIndex<fileCount;fileIndex++)
        {
            checkCondition(this.fileList[fileIndex].toCharArray(), this.option.toCharArray());
        }

        return this.result;
    }
}
