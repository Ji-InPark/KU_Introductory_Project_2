import java.util.ArrayList;
import java.io.File;

public class OptionMaxdepth {
    private FileList fileList;

    public OptionMaxdepth(FileList fileList){
        this.fileList = fileList;

    }
    /*
    public ArrayList<String> getResult(){
        return maxdepth(this.fileList, this.path, this.depth);
    }
     */
    public ArrayList<String> maxdepth(int depth){
        if(depth < 0) { // invalid argument
            System.out.println("depth must be zero or more");
            return null;
        }
        ArrayList<String> tmp = new ArrayList<>();
        for(int i = 0; i < this.fileList.getSize(); i++){
            if(this.fileList.getDepthList().get(i) <= depth){
                tmp.add(this.fileList.getFileList().get(i).getPath());
            }
        }
        return tmp;
    }
    /*
    public ArrayList<String> maxdepth(File file, String path, int depth){

        if(depth < 0){ // invalid argument
            return null;
        }
        // valid argument below
        boolean ends = path.endsWith("/");
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add(file.getName());
        if(depth == 0){
            return tmp;
        }
        for(File i : file.getFiles()){
            if(ends == true) {
                tmp.addAll(maxdepth(i, path + i.getName(), depth - 1));
            }
            else{
                tmp.addAll(maxdepth(i, path + "/" + i.getName(), depth - 1));
            }
        }
        return tmp;
    }
     */

}
