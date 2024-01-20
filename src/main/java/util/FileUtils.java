package util;

import Http.HttpRequest;
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
    public static Optional<byte[]> readFile(HttpRequest httpRequest, String uri) {

        int index = uri.lastIndexOf(".");
        String type = uri.substring(index + 1);
        try {
            if (type.equals("html")) {
                return Optional.of(Files.readAllBytes(new File(htmlPath + uri).toPath()));
            }
            return Optional.of(Files.readAllBytes(new File(resourcePath + uri).toPath()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<byte[]> read404File(String uri) {
        try {
            return Optional.of(Files.readAllBytes(new File(htmlPath + uri).toPath()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
}
