import java.util.ArrayList;
import java.io.File;

public class OptionType {
    private FileList fileList;

    public OptionType(FileList fileList){
        this.fileList = fileList;
    }

    /*
    public ArrayList<String> getResult(){
        return maxtype(this.file, this.path, this.type);
    }
     */
    public ArrayList<String> type2(String type){
        if(!type.equals("d") && !type.equals("f")){ // invalid argument
            return null;
        }
        ArrayList<String> tmp = new ArrayList<>();
        if(type.equals("d")){
            for(int i = 0; i < this.fileList.getSize(); i++){
                File f = fileList.getFileList().get(i);
                if(f.isDirectory()){
                     tmp.add(f.getName());
                }
            }
        }
        else{
            for(int i = 0; i < this.fileList.getSize(); i++){
                File f = fileList.getFileList().get(i);
                if(f.isFile()){
                    tmp.add(f.getName());
                }
            }
        }
        return tmp;
    }
/*
    public ArrayList<String> maxtype(File file, String path, String type){

        if(!type.equals("d") && !type.equals("f")){ // invalid argument
            return null;
        }
        // valid argument below
        boolean ends = path.endsWith("/");
        ArrayList<String> tmp = new ArrayList<String>();
        String type_tmp = file.getType();
        if(type_tmp.equals(type)) {
            tmp.add(path);
        }
        for(File i : file.getChildren()){
            if(ends == true){
                tmp.addAll(maxtype(i, path + i.getName(), type));
            }
            else{
                tmp.addAll(maxtype(i, path + "/" + i.getName(), type));
            }
        }

        return tmp;
    }
*/
}
