package basic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class TestStream {

    public static void main(String[] args) throws IOException {
        TestStream testStream = new TestStream();
        java.io.ByteArrayOutputStream arrOut = new java.io.ByteArrayOutputStream();
        ByteArrayOutputStream outputStream = (ByteArrayOutputStream)arrOut;

        ByteArrayOutputStream temOut = outputStream == null ? new ByteArrayOutputStream() : outputStream;
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(temOut);
        String text = "Hello,";
        byte[] myvar = "Any String you want".getBytes();
        gzipOutputStream.write(myvar);
        System.out.println(arrOut);
    }


}
