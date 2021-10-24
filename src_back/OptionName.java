package src;

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

    private void checkCondition(char[] filename, char[] option)
    {
        int filenameLength = filename.length;
        int optionLength = option.length;

        int filenameIndex = 0;
        int optionIndex = 0;

        while(filenameIndex < filenameLength && optionIndex < optionLength)
        {
            if(option==)
            if(filename[filenameIndex]==option[optionIndex])
            {
                filenameIndex++;
                optionIndex++;
            }

            else
            {
                // *나 ?같은 특수 옵션 처리
            }
        }

        this.result.append(new String(filename));
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
