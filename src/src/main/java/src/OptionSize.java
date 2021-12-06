package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OptionSize implements Option {
    private char[] UNITS = {'B', 'M', 'k', 'G'};

    private String arg;

    private long fileLength = 0;
    private char checkDirection = 0;

    private List<File> fileList;

    public OptionSize(List<File> fileList, String arg) {
        this.fileList = fileList;
        this.arg = arg;

        this.checkArg();
    }


    @Override
    public List<File> analyze() {
        List<File> results= new ArrayList<>();
        for(File file: fileList){
            if(checkDirection== '-'){
                if (file.length() < fileLength) {
                    results.add(file);
                }
            }else if(checkDirection== 0){
                if (file.length() == fileLength) {
                    results.add(file);
                }
            }else if(checkDirection== '+'){
                if (file.length() > fileLength) {
                    results.add(file);
                }
            }
        }
        return results;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        String arg = this.arg;
        if (arg.charAt(0) == '+' || arg.charAt(0) == '-') {
            checkDirection = arg.charAt(0);
            arg = arg.substring(1);
        }

        char unit = arg.charAt(arg.length() - 1);
        if (unit >= 'A' && unit <= 'z') {
            boolean validUnit = false;
            for (int i = 0; i < UNITS.length; i++) {
                if (UNITS[i] == unit) {
                    validUnit = true;
                    break;
                }
            }
            arg = arg.substring(0, arg.length() - 1);

            if (!validUnit)
                throw new IllegalArgumentException("올바른 Size 단위가 아닙니다");
        } else {
            unit = 'B';
        }


        try {
            fileLength = Integer.parseInt(arg);
            switch (unit) {
                case 'k':
                    fileLength *= 1024;
                    break;
                case 'M':
                    fileLength *= 1024 * 1024;
                    break;
                case 'G':
                    fileLength *= 1024 * 1024 * 1024;
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("올바른 Size값이 아닙니다");
        }
    }

    @Override
    public String getSymbol() {
        return "-size";
    }
}
