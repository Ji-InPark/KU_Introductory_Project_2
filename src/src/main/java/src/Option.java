package src;

import java.io.File;
import java.util.List;

public interface Option {

    List<File> analyze();

    void checkArg() throws IllegalArgumentException;


    String getSymbol();
}
