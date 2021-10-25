package src;

public class Cfind {

    public void help()
    {
        System.out.println("" +
                "-size N[kMG] : 크기로 검색을 한다. [kMG] 는 다음과 같다.\n" +
                "k : 킬로바이트(kilobytes)를 의미\n" +
                "M : 메가바이트(megabytes)를 의미\n" +
                "G : 기가바이트(gigabytes)를 의미\n" +
                "ex)\n" +
                "default value는 byte로 설정\n" +
                "-size 10 : 정확히 10B의 파일을 의미\n" +
                "-size +16M : 16MB 이상의 파일을 의미\n" +
                "-size -20k : 20KB 이하의 파일을 의미\n" +
                "-size 11G : 정확히 11GB의 파일을 의미\n" +
                "\n" +
                "-type c : c type의 파일을 검색한다.\n" +
                "c는 다음 중 하나의 값을 갖는다.\n" +
                "d : 디렉토리(directory)를 의미\n" +
                "f : 바로가기를 포함한 일반 파일(regular file)을 의미\n" +
                "\n" +
                "-mtime N : 가장 최근에 수정된 시간(last modified time)을 기준으로 검색한다. 여기서 N의 값은 다음과 같다.\n" +
                "N : yyyyMMddThhmmss\n" +
                "T는 반드시 존재하며 설명을 위해 T를 기준으로 왼쪽 파트, 오른쪽 파트로 나눈다.\n" +
                "+ 또는 -가 있을 경우에는 각각 이후, 이전을 나타낸다.\n" +
                "예시는 2021년 10월 05일 10시 20분 30초에 명령어를 실행 했을 때로 가정한다.\n" +
                "ex) 왼쪽 파트\n" +
                "“yyyyMMdd” : yyyy, MM, dd 셋 다 입력받은 값으로 설정\n" +
                "“MMdd” : yyyy는 2021로 설정\n" +
                "“dd” : yyyy는 2021, MM은 10으로 설정\n" +
                "“” : yyyy는 2021, MM은 10, dd는 05로 설정\n" +
                "\n" +
                "ex) 오른쪽 파트\n" +
                "“hhmmss” : hh, mm, ss 셋 다 입력받은 값으로 설정\n" +
                "“hhmm” : ss는 00으로 대체\n" +
                "“hh” : mm, ss 둘 다 00으로 대체\n" +
                "“” : hh, mm, ss 셋 다 00으로 대체\n" +
                "따라서 -mtime T를 입력할 시 20211005000000으로 대체된다.\n" +
                "\n" +
                "-maxdepth levels : cfind는 기본적으로 recursive하게 작동하고, 이에 하위 디렉토리를 얼마나 깊게 탐색할 건지에 대한 option이다.\n" +
                "ex)\n" +
                "cfind . 또는 cfind ./ : 현재 디렉토리를 포함하여 하위 디렉토리를 모두 탐색한다.\n" +
                "cfind . -maxdepth 0 : .(현재 디렉토리)만 검색한다.\n" +
                "cfind . -maxdepth 1 : .을 포함하여 .에 존재하는 파일 및 깊이가 1인 디렉토리도 검색한다.\n" +
                "\n" +
                "-name [PATTERN] : [PATTERN]을 포함하는 이름을 가진 디렉토리 및 파일을 검색한다. [PATTERN]은 Meta Characters를 적용한 문자열이 된다.\n" +
                "ex)\n" +
                "-name “hi” : hi라는 이름의 파일 및 디렉토리를 의미\n" +
                "-name “*ab*” : ab가 포함된 문자열을 이름으로 가진 파일 및 디렉토리를 의미\n");
    }

}
