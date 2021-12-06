package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OperatorAND implements Operator {
    public OperatorAND(){}

    private List<File> operand1;
    private List<File> operand2;
    public OperatorAND setValues(List<File> operand1, List<File> operand2){
        this.operand1= operand1;
        this.operand2= operand2;

        return this;
    }

    @Override
    public List<File> analyze() {
        checkArg();

        List<File> results= new ArrayList<>();

        for(File file: operand1){
            if(operand2.contains(file))
                results.add(file);
        }

        return results;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        if(operand1== null || operand2== null)
            throw new IllegalArgumentException("AND 연산자는 두 개의 피연산자가 필요합니다");
    }

    @Override
    public String getSymbol() {
        return "-and";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
