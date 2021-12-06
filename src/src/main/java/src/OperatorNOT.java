package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OperatorNOT implements Operator {
    public OperatorNOT() {
    }

    private List<File> operand;
    private List<File> allFiles;

    public OperatorNOT setValues(List<File> allFiles, List<File> operand) {
        this.allFiles= allFiles;
        this.operand = operand;

        return this;
    }

    @Override
    public List<File> analyze() {
        checkArg();

        List<File> results = new ArrayList<>(allFiles);
        for(File file: operand){
            results.remove(file);
        }
        return results;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        if (allFiles == null)
            throw new IllegalArgumentException("NOT 연산을 위한 전체 파일 정보가 주어지지 않았습니다");
        if (operand == null)
            throw new IllegalArgumentException("NOT 연산자는 한 개의 피연산자가 필요합니다");
    }

    @Override
    public String getSymbol() {
        return "-not";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
