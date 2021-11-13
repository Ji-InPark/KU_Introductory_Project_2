package src;

import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class OptionType implements Option{
    private List<File> fileList;
    private String type;

    public OptionType(List<File> fileList, String type){
        this.type = type;
        this.fileList = fileList;

        checkArg();
    }

    /*
    public ArrayList<String> getResult(){
        return maxtype(this.file, this.path, this.type);
    }
     */
    public ArrayList<File> OptionType(){
        ArrayList<File> tmp = new ArrayList<>();

        if(type.equals("d")){ // type == "d"
            for(File f : this.fileList){
                if(f.isDirectory()){
                    tmp.add(f);
                }
            }
        }
        else{ // type == "f"
            for(File f : this.fileList){
                if(f.isFile()){
                    tmp.add(f);
                }
            }
        }

        return tmp;
    }

    @Override
    public List<File> analyze() {
        ArrayList<File> result = OptionType();
        return result;
    }

    @Override
    public void checkArg() throws IllegalArgumentException {
        if(type.length() != 1){
            throw new IllegalArgumentException("type must be either d or f");
        }
        else{
            if(!type.equals("d") && !type.equals("f")){
                throw new IllegalArgumentException("type must be either d or f");
            }
        }

    }

    @Override
    public String getSymbol() {
        return "-type";
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