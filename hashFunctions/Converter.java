
import java.security.MessageDigest;
import static java.security.MessageDigest.getInstance;
import static java.util.Arrays.copyOfRange;

public class Converter
{
    final byte[] file;
    final byte[][] blocks;
    final byte[][] matrizH;
    final byte[] h0;
    final byte[] hn_1;
    final String h0String;
    final String hn_1String;
    final int numberOfBlocks;
    final int lastBlockSize;
    final int numberOfBytes;

    public Converter(byte[] file) throws Exception
    {
        this.file = file;
        this.blocks = convertInBlocks(file);
        this.matrizH = convertMatrizH(blocks);
        this.h0 = matrizH[0];
        this.hn_1 = matrizH[matrizH.length-1];
        this.h0String = byteArrayToHexString(h0);
        this.hn_1String = byteArrayToHexString(hn_1);
        this.numberOfBlocks = blocks.length;
        this.lastBlockSize = blocks[blocks.length-1].length;
        this.numberOfBytes = file.length;
    }

    byte[][] convertInBlocks(byte[] array) {
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

    byte[][] convertMatrizH(byte[][] blocks) throws Exception {

        byte[][] matrizH = new byte[blocks.length][];
        MessageDigest digest = getInstance("SHA-256");

        int last = blocks.length - 1;
        byte[] encoded = {};

        for (int i = last; i >= 0; i--) {
            encoded = digest.digest(combine(blocks[i], encoded));
            matrizH[i] = encoded;
        }
        return matrizH;
    }

    byte[] combine(byte[] a, byte[] b) {
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    String byteArrayToHexString(byte[] b) {
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

    public String toString() {
        return "Total number of bytes: " + numberOfBytes
            + " Last block size: " + lastBlockSize
            + " Number of blocks: " + numberOfBlocks
            + "\nh[0]: " + h0String
            + "\nh[n-1]: " + hn_1String
        ;
    }
}
