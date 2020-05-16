package classes;

import static classes.Read.read;
import static java.security.MessageDigest.getInstance;
import static java.util.Arrays.copyOfRange;

import java.security.MessageDigest;

public class App {
    public static void main(final String[] args) throws Exception {

        validateFunctions();

        final String h0String = generateH0("FuncoesResumo - Hash Functions.mp4");
        System.out.println("\nh0 = " + h0String + "\n");
    }

    private static String generateH0(String path) throws Exception {
        final byte[] file = read(path);
        final byte[][] matriz = convert(file);
        final byte[][] encoded = convert(matriz);
        final byte[] h0 = encoded[0];
        final String h0String = byteArrayToHexString(h0);
        return h0String;
    }

    private static void validateFunctions() throws Exception {

        byte[] file = Read.read("FuncoesResumo - SHA1.mp4");
        if (file.length != 15948514) {
            throw new Exception("erro de leitura do arquivo: " + file.length);
        }
        byte[][] matriz = convert(file);
        if (matriz.length != 15575) {
            throw new Exception("erro de conversão de bytes: " + matriz.length);
        }
        if (matriz[matriz.length - 1].length != 738) {
            throw new Exception("erro nos ultimos bytes: " + matriz[matriz.length - 1].length);
        }

        byte[][] encoded = convert(matriz);

        String wordExpected;
        byte[] bytesReceived;
        String wordEncoded;

        wordExpected = "37d88ff100aaf4c63bb828ff1a89f99af2123e143bd758d0eb1573a044e74c84";
        bytesReceived = encoded[encoded.length - 1];
        wordEncoded = byteArrayToHexString(bytesReceived);
        if (!wordEncoded.equals(wordExpected.toUpperCase()))
            throw new Exception("erro no hex da ultima palavra: " + wordEncoded);

        wordExpected = "302256b74111bcba1c04282a1e31da7e547d4a7098cdaec8330d48bd87569516";
        bytesReceived = encoded[0];
        wordEncoded = byteArrayToHexString(bytesReceived);
        if (!wordEncoded.equals(wordExpected.toUpperCase()))
            throw new Exception("erro no h0: " + wordEncoded);
    }

    private static byte[][] convert(byte[] array) {
        int size = array.length / 1024;
        int mod = array.length % 1024;
        if (mod > 0)
            size++;

        byte[][] matriz = new byte[size][];
        int start = 0;
        int end = 0;
        for (int i = 0; i < matriz.length; i++) {
            if (start + 1024 <= array.length)
                end = start + 1024;
            else
                end = array.length;
            matriz[i] = copyOfRange(array, start, end);
            start = end;
        }
        return matriz;
    }

    private static byte[][] convert(byte[][] matrizOld) throws Exception {

        byte[][] matriz = new byte[matrizOld.length][];
        MessageDigest digest = getInstance("SHA-256");

        int last = matrizOld.length - 1;
        byte[] encoded = {};

        for (int i = last; i >= 0; i--) {
            encoded = digest.digest(combine(matrizOld[i], encoded));
            matriz[i] = encoded;
        }
        // matriz[0] = digest.digest(matriz[1]);
        return matriz;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] combine(byte[] a, byte[] b) {
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}