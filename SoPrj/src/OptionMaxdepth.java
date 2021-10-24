import java.util.ArrayList;

public class OptionMaxdepth {
    private String path;
    private File file;
    private int depth;

    public OptionMaxdepth(String path, File file, int depth){
        this.path = path;
        this.file = file;
        this.depth = depth;
    }
    public ArrayList<String> getResult(){
        return maxdepth(this.file, this.path, this.depth);
    }
    public ArrayList<String> maxdepth(File file, String path, int depth){

        if(depth < 0){ // invalid argument
            return null;
        }
        // valid argument below
        boolean ends = path.endsWith("/");
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add(path);
        if(depth == 0){
            return tmp;
        }
        for(File i : file.getChildren()){
            if(ends == true) {
                tmp.addAll(maxdepth(i, path + i.getName(), depth - 1));
            }
            else{
                tmp.addAll(maxdepth(i, path + "/" + i.getName(), depth - 1));
            }
        }
        return tmp;
    }

}
