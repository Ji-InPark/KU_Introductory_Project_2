import java.util.ArrayList;

public class Cfind {
    private File root;
    private File curr;
    private ArrayList<File> result;
    public void filter(String path){
        // this method find a certain File class through the input
        // input can be divided into one or more parts
        // [path] [expressions1] [expression2....]
        // we can get path and File Node from this

    }
    public static void printArrayList(ArrayList<String> list){ // 임시로 arraylist 출력하는 함수
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }

    public static void main(String[] args){
        Cfind cfind = new Cfind();
        File root = new File("/", "d");
        cfind.root = root;
        File node1_0 = new File("1_0", "d");
        File node1_1 = new File("1_1", "d");
        File node1_2 = new File("1_2");
        File node2_0 = new File("2_0");
        File node2_1 = new File("2_1");
        File node2_2 = new File("2_2");
        File node2_3 = new File("2_3");
        File node2_4 = new File("2_4");
        File node2_5 = new File("2_5", "d");
        File node3_1 = new File("3_1", "d");
        File node3_2 = new File("3_2");
        cfind.curr = node1_1;
        node1_0.setParent(root);
        node1_1.setParent(root);
        node1_2.setParent(root);
        node2_0.setParent(node1_0);
        node2_1.setParent(node1_0);
        node2_2.setParent(node1_0);
        node2_3.setParent(node1_1);
        node2_4.setParent(node1_1);
        node2_5.setParent(node1_1);
        node3_1.setParent(node2_5);
        node3_2.setParent(node2_5);

        /* filter로 인해 path랑 file이 주어졌다고 가정하고 대입
           아래의 내용은 실제로는 filter 내부에서 실행될 내용
           1_0, 1_1, 2_5, 3_1 -> directory (of course they're files as well)
           the others -> file
         */
        OptionMaxdepth options1 = new OptionMaxdepth("/", cfind.root, 2);
        OptionMaxdepth options2 = new OptionMaxdepth("./", cfind.curr, 3);
        OptionType options3 = new OptionType("/", cfind.root, "d");
        OptionType options4 = new OptionType(".", cfind.curr, "f");
        System.out.println("test1");
        printArrayList(options1.getResult());
        // -> $ ./cfind / -maxdepth 2 (curr : root)
        System.out.println("test2");
        printArrayList(options2.getResult());
        // -> $ ./cfind . -maxdepth 3 (curr : /1_1)

        /*
        Result is as follows:
            test1
            /
            /1_0
            /1_0/2_0
            /1_0/2_1
            /1_0/2_2
            /1_1
            /1_1/2_3
            /1_1/2_4
            /1_1/2_5
            test2
            ./
            ./2_3
            ./2_4
            ./2_5
            ./2_5/3_1
        */
        System.out.println("\n\n");
        System.out.println("test3");
        printArrayList(options3.getResult());
        // -> $ ./cfind / -type d (starts from root)
        System.out.println("test4");
        printArrayList(options4.getResult());
        // -> $ ./cfind . -type f (starts from /1_1)

        /*
        Result is as follows:
            test3
            /
            /1_0
            /1_1
            /1_1/2_5
            /1_1/2_5/3_1
            test4
            ./2_3
            ./2_4
            ./2_5/3_2
        */
    }
}
