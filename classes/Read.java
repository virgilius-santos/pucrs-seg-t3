package classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Read {
   public static byte[] read(String stringPath) throws IOException {
      Path path = Paths.get(stringPath);
      return Files.readAllBytes(path);
   }
}