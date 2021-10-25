package src;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FileList {
    private File root;                      // cfind에서 입력으로 들어오는 root 파일
    private ArrayList<File> fileList;       // 순회 완료한 파일들 저장한 리스트
    private ArrayList<Integer> depthList;   // 각 파일들의 depth 저장한 리스트
    private boolean[] result;               // 출력 때 사용할 어레이 (true면 조건 만족, false면 불만족)

    public FileList(String path)
    {
        this.root = new File(path);
        fileList = new ArrayList<File>();
        depthList = new ArrayList<Integer>();

        makeList(this.root, 0);
    }

    // 파일 트리 순회 및 리스트 생성
    // 생성할 때 root, 0 을 넘겨주면 됨
    public void makeList(File dir, int level)
    {
        if(level == 0)
        {
            fileList.add(dir);
            depthList.add(level);
        }

        File[] files = dir.listFiles();

        for(int i = 0; i < files.length; i++)
        {
            fileList.add(files[i]);
            depthList.add(level + 1);
            if(files[i].isDirectory())
                makeList(files[i], level + 1);
        }

        if(level == 0)
            result = new boolean[fileList.size()];
    }

    public int getSize()
    {
        return fileList.size();
    }

    public File getFile(int index)
    {
        return fileList.get(index);
    }

    public void setResult(int index, boolean possible)
    {
        result[index] = possible;
    }

    public boolean getResult(int index)
    {
        return result[index];
    }
}
