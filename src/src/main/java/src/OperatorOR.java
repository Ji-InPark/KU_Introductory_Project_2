package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OperatorOR implements Operator {
    public OperatorOR(){}

    private List<File> operand1;
    private List<File> operand2;
    public OperatorOR setValues(List<File> operand1, List<File> operand2){
        this.operand1= operand1;
        this.operand2= operand2;

        return this;
    }

    @Override
    public List<File> analyze() {
        checkArg();

        List<File> results= new ArrayList<>(operand1);

        for(File file: operand2){
            if(!results.contains(file))
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
        return "-or";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
