package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class OptionSize {

    FileList fileList;      // 순회된 file들을 저장하는 클래스
    int NP, N, kMG;         // (NP: -1은 -, 0은 생략, 1은 +), N: 입력 숫자, (kMG: 1은 k, 2는 M, 3은 G)


    public OptionSize(FileList fileList)
    {
        this.fileList = fileList;
    }

    public void SetOption(int NP, int N, int kMG)
    {
        this.NP = NP;
        this.N = N;
        this.kMG = kMG;
    }

    public void Analyze() throws IOException {
        if(NP == -1)        // N보다 작은 것 탐색
        {
            for(int i = 0; i < fileList.getSize(); i++)        // 여기서 fileList는 순회가 끝난 파일들의 어래이리스트
            {
                File file = fileList.getFile(i);
                if(Files.size(file.toPath()) < NP * Math.pow(1024, kMG))
                {
                    fileList.setResult(i, true);              // 여기서 setFind(index, boolean)는 해당 파일이 조건에 충족하는지(true, false)를 알려줌 (fileList 클래스에 boolean ArrayList 혹은 Array 가 있음)
                }
                else
                {
                    fileList.setResult(i, false);
                }
            }
        }
        else if(NP == 0)    // N과 정확히 같은 것 탐색
        {
            for(int i = 0; i < fileList.getSize(); i++)        // 여기서 fileList는 순회가 끝난 파일들의 어래이리스트
            {
                File file = fileList.getFile(i);
                if(Files.size(file.toPath()) == NP * Math.pow(1024, kMG))
                {
                    fileList.setResult(i, true);              // 여기서 setFind(index, boolean)는 해당 파일이 조건에 충족하는지(true, false)를 알려줌 (fileList 클래스에 boolean ArrayList 혹은 Array 가 있음)
                }
                else
                {
                    fileList.setResult(i, false);
                }
            }
        }
        else                // N보다 큰 것 탐색
        {
            for(int i = 0; i < fileList.getSize(); i++)        // 여기서 fileList는 순회가 끝난 파일들의 어래이리스트
            {
                File file = fileList.getFile(i);
                if(Files.size(file.toPath()) > NP * Math.pow(1024, kMG))
                {
                    fileList.setResult(i, true);              // 여기서 setFind(index, boolean)는 해당 파일이 조건에 충족하는지(true, false)를 알려줌 (fileList 클래스에 boolean ArrayList 혹은 Array 가 있음)
                }
                else
                {
                    fileList.setResult(i, false);
                }
            }
        }
    }
}
