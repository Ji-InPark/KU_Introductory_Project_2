import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class OptionMaxdepth implements Option {
    private ArrayList<File> fileList;
    int depth;

    public OptionMaxdepth(ArrayList<File> fileList, int depth){
        this.fileList = fileList;
        this.depth = depth;

        checkArg();
    }
    /*
    public ArrayList<String> getResult(){
        return maxdepth(this.fileList, this.path, this.depth);
    }
     */

    @Override
    public List<File> analyze(){
        ArrayList<File> result;
        result = maxdepth(this.fileList.get(0), this.depth);
        return result;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        if(this.depth < 0){
            throw new IllegalArgumentException("depth must be zero or more")
        }
    }

    @Override
    public String getSymbol() {
        return "-maxdepth";
    }

    public ArrayList<File> maxdepth(File file, int tmp_depth){

        ArrayList<File> tmp = new ArrayList<File>();
        tmp.add(file);
        if(tmp_depth == 0){
            return tmp;
        }
        for(File f : fileList){
            tmp.addAll(maxdepth(f, tmp_depth-1));
        }

        return tmp;
    }
    /*
    public ArrayList<String> maxdepth(File file, int depth){
        ArrayList<File> tmp = new ArrayList<File>();
        tmp.add(file);
        if(depth == 0){
            return tmp;
        }
        for(File i : file.getFiles()){
            tmp.addAll(maxdepth(i, depth - 1);
        }

        return tmp;
    }
     */

}
