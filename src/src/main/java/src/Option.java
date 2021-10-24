package src;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.ArrayList;

public interface Option {
    @NotNull
    ArrayList<File> analyze();

    void checkArg() throws IllegalArgumentException;
}
