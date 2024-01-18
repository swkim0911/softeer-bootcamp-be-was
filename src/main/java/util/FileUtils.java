package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static byte[] readFile(String fileName) {
        try {
            return Files.readAllBytes(new File(fileName).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "파일을 찾을 수 없습니다.".getBytes();
        }
    }

    public static String getFileType(String uri) {
        int index = uri.lastIndexOf(".");
        return uri.substring(index+1);
    }
}
