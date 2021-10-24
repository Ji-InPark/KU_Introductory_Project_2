import java.util.ArrayList;

public class OptionType {
    private String path;
    private File file;
    private String type;

    public OptionType(String path, File file, String type){
        this.path = path;
        this.file = file;
        this.type = type;
    }

    public ArrayList<String> getResult(){
        return maxtype(this.file, this.path, this.type);
    }

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

}
