package src;

import java.io.File;
import java.util.List;

public class OperatorGroupOpen implements Operator {
    public OperatorGroupOpen() {
    }

    @Override
    public List<File> analyze() {
        throw new IllegalStateException("이 연산자는 실행 가능한 연산자가 아닙니다");
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
    }

    @Override
    public String getSymbol() {
        return "(";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
