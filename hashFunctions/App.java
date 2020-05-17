
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.security.MessageDigest.getInstance;
import static java.util.Arrays.copyOfRange;

import java.security.MessageDigest;

public class App {

    static Converter converter;

    public static void main(final String[] args) throws Exception {

        String fileName;
        if (args.length == 0)
            fileName = "FuncoesResumo - Hash Functions.mp4";
        else
            fileName = args[0];

        byte[] file = read(fileName);
        converter = new Converter(file);
        
        System.out.println("\nNome do arquivo: " + fileName);
        System.out.println(converter);
    }

    public static byte[] read(String stringPath) throws IOException {
        Path path = Paths.get(stringPath);
        return Files.readAllBytes(path);
    }
}