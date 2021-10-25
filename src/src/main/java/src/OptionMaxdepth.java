package src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

public class OptionMaxdepth implements Option {
    private ArrayList<File> fileList;
    private File root;
    int depth;

    public OptionMaxdepth(ArrayList<File> fileList, int depth){
        this.fileList = fileList;
        this.depth = depth;
        this.root = fileList.get(0);
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
            throw new IllegalArgumentException("depth must be zero or more");
        }
    }

    @Override
    public String getSymbol() {
        return "-maxdepth";
    }


    public ArrayList<File> maxdepth(File file, int tmp_depth){
        ArrayList<File> tmp = new ArrayList<File>();

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