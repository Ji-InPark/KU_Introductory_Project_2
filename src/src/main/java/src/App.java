package src;

import src.OptionName;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] testfiles = new String[]{"test", "test2"};
        OptionName testName = new OptionName(testfiles);
        testName.setOption("asd");
    }
}
