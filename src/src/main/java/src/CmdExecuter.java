package src;


import java.io.File;
import java.util.List;
import java.util.Stack;

public class CmdExecuter {
    private List<Option> options;
    private List<File> allFiles;

    public CmdExecuter(List<File> allFiles, List<Option> options) {
        this.allFiles = allFiles;
        this.options = options;
    }

    public List<File> exeucte() {
        List<File> result = allFiles;
        if (!options.isEmpty()) {
//            System.out.println("end"+ options);
            Stack<List<File>> values = new Stack<>();
//            System.out.println("options(ops): "+ options);
            for (int i = 0; i < options.size(); i++) {
                Option currentOption = options.get(i);
//                System.out.println("Stack: "+ values);
//                System.out.println("current option(op): "+ currentOption);

                if (currentOption instanceof Operator) {
                    //operator
                    Operator operator = (Operator) currentOption;
                    switch (operator.getSymbol()) {
                        case "-and":
                            try {
                                List<File> andResult = ((OperatorAND) operator).setValues(values.pop(), values.pop()).analyze();
                                values.push(andResult);
                            } catch (Exception e) {
//                                e.printStackTrace();
                                throw new IllegalStateException("-and 연산을 위해서는 두 개의 인자가 필요합니다");
                            }
                            break;
                        case "-or":
                            try {
                                List<File> orResult = ((OperatorOR) operator).setValues(values.pop(), values.pop()).analyze();
                                values.push(orResult);
                            } catch (Exception e) {
//                                e.printStackTrace();
                                throw new IllegalStateException("-or 연산을 위해서는 두 개의 인자가 필요합니다");
                            }
                            break;
                        case "-not":
                            try {
                                List<File> notResult = ((OperatorNOT) operator).setValues(allFiles, values.pop()).analyze();
                                values.push(notResult);
                            } catch (Exception e) {
//                                e.printStackTrace();
                                throw new IllegalStateException("-not 연산을 위해서는 뒤에 하나의 인자가 필요합니다");
                            }
                            break;
                        default:
                            throw new IllegalStateException("올바르지 않은 연산자입니다");
                    }
                } else {
                    //files
                    values.push(currentOption.analyze());
                }
            }
            result = values.pop();
//            System.out.println("end"+ values);
        }

        if (result != null) {
            Utils.printResult(result);
        }

        return result;
    }

}
