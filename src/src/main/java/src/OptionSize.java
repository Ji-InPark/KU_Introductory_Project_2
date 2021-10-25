package src;

import java.io.File;
import java.util.List;

public class OptionSize implements Option {
    FileList fileList;      // 순회된 file들을 저장하는 클래스

    private char[] UNITS = {'B', 'M', 'k', 'G'};

    private String arg;

    private long fileLength = 0;
    private char checkDirection = 0;

    public OptionSize(FileList fileList, String arg) {
        this.fileList = fileList;
        this.arg = arg;

        this.checkArg();
    }


    @Override
    public List<File> analyze() {
        for (int i = 0; i < fileList.getSize(); i++) {
            File file = fileList.getFile(i);
            if(checkDirection== '-'){
                if (file.length() < fileLength) {
                    fileList.setResult(i, true);
                } else {
                    fileList.setResult(i, false);
                }
            }else if(checkDirection== 0){
                if (file.length() == fileLength) {
                    fileList.setResult(i, true);
                } else {
                    fileList.setResult(i, false);
                }
            }else if(checkDirection== '+'){
                if (file.length() > fileLength) {
                    fileList.setResult(i, true);
                } else {
                    fileList.setResult(i, false);
                }
            }
        }

        return fileList.getTargetFileList();
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
        return "size";
    }
}
