import java.util.ArrayList;

public class File {
    private String name; // file Name
    private File parent;
    private String type;
    private ArrayList<File> children;

    public File(String name){
        this.name = name;
        this.type = "f";
        this.parent = null;
        this.children = new ArrayList<File>();
    }

    public File(String name, String type){
        this.type = type;
        this.name = name;
        this.type = type;
        this.parent = null;
        this.children = new ArrayList<File>();
    }

    public String getType(){
        return this.type;
    }
    public String getName(){
        return this.name;
    }

    public File getParent() {
        return this.parent;
    }

    public ArrayList<File> getChildren(){
        return this.children;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setParent(File parent) {
        this.parent = parent;
        parent.getChildren().add(this);

    }

}
