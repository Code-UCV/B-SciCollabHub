package collab.backend.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class FileUtil {
    public static byte[] compressFile(byte[] data) {
        Deflater compress = new Deflater();
        compress.setLevel(Deflater.BEST_COMPRESSION);
        compress.setInput(data);
        compress.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4*1024];

            while(!compress.finished()) {
                int size = compress.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
