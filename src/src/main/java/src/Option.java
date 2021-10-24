package src;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.List;

public interface Option {
    @NotNull
    List<File> analyze();

    void checkArg() throws IllegalArgumentException;

    @NotNull
    String getSymbol();
}
