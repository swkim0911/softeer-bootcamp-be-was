package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String htmlPath = "src/main/resources/templates";
    private static final String resourcePath = "src/main/resources/static";
    public static Optional<byte[]> readFile(String uri) {
        String fileType = UriParser.getFileType(uri);
        try {
            if (fileType.equals("html")) {
                return Optional.of(Files.readAllBytes(new File(htmlPath + uri).toPath()));
            }
            return Optional.of(Files.readAllBytes(new File(resourcePath + uri).toPath()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
    //todo readFile 함수와 로직이 이제 비슷하니까 함수로 묶기
    public static Optional<byte[]> read404File(String uri) {
        try {
            return Optional.of(Files.readAllBytes(new File(htmlPath + uri).toPath()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
}
