package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String htmlPath = "src/main/resources/templates";
    private static final String resourcePath = "src/main/resources/static";
    public static Optional<byte[]> readFile(String uri) {
        String fileType = UriParser.getFileType(uri);
        try {
            if (fileType.equals("html")) {
                return Optional.of(readFileBytes(htmlPath + uri));
            }
			return Optional.of(readFileBytes(resourcePath + uri));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

	private static byte[] readFileBytes(String filePath) throws IOException {
		File file = new File(filePath);
		try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead); //bos에 buffer에서 읽은 만큼 쓰기
			}
			return bos.toByteArray();
		}
	}
}
