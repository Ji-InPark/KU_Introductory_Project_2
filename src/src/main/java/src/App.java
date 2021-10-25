package src;

import src.OptionName;
/**
 * Hello world!
 *
 * 언급된 옵션
 * ABC**DEF
 * ABC*DEF
 * *.pdf
 * *전기프*
 * B분반??팀
 * 20* 전기프 ?분반 *팀
 */

public class App 
{
    public static void main( String[] args )
    {
        String[] testfiles = new String[]{"test", "ㄴㅁㅇ러ㅏ마닐엄니ㅏㄹ전기프나ㅓ리ㅏㅁ나ㅓ리ㅏㅁ넝ㄹ", "2021 전기프 에이분만 굿아아아아팀", "asdfrhenohn.pdf", "test.pdf"};
        OptionName testName = new OptionName(testfiles);
        testName.setOption("*전기프");
        testName.analyze();
        System.out.println("the end");
    }
}
