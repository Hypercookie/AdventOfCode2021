package utils;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class Utilities {


    public static Stream<String> readInFile(String loc) throws IOException {
        return Files.lines(Paths.get(loc));
    }


}
