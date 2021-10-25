package src;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class OptionMaxdepth implements Option {
    private File root;
    private int depth;

    public OptionMaxdepth(File root, int depth){
        this.depth = depth;
        this.root= root;

        checkArg();
    }

    @Override
    public List<File> analyze(){
        ArrayList<File> result = maxdepth(this.root, this.depth);
        return result;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        if(this.depth < 0){
            throw new IllegalArgumentException("maxdepth값은 0 이상의 정수로 주어져야 합니다");
        }
        if(!root.exists()){
            throw new IllegalArgumentException("유효하지 않은 파일 경로입니다");
        }
        if(!root.isDirectory()){
            throw new IllegalArgumentException("해당 경로는 디렉터리가 아닙니다");
        }
    }

    @Override
    public String getSymbol() {
        return "-maxdepth";
    }


    public ArrayList<File> maxdepth(File file, int tmp_depth){
        ArrayList<File> tmp = new ArrayList<>();

        if(file != null) {
            tmp.add(file);
        }
        if(tmp_depth == 0){
            return tmp;
        }
        if(file.isDirectory()) {
            for (File i : file.listFiles()) {
                tmp.addAll(maxdepth(i, tmp_depth - 1));
            }
        }

        return tmp;
    }


}