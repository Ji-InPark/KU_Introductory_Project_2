package src;



import java.util.List;

import java.util.ArrayList;

public class OperatorUtil  {
    private static OperatorUtil inst;

    public static OperatorUtil getInstance(){
        if(inst == null)
            inst= new OperatorUtil();

        return inst;
    }

    private final List<Operator> operators= new ArrayList<>();
    private OperatorUtil(){
        operators.add(new OperatorAND());
        operators.add(new OperatorOR());
        operators.add(new OperatorNOT());
        operators.add(new OperatorGroupOpen());
        operators.add(new OperatorGroupClose());
    }

    public Operator getOperator(String symbol){
        for(Operator operator: operators){
            if(operator.getSymbol().equals(symbol))
                return operator;
        }

        return null;
    }

    public int comparePriority(String symbol1, String symbol2){
        Operator op1= getOperator(symbol1);
        Operator op2= getOperator(symbol2);

        return op1.getPriority()- op2.getPriority();
    }
}
