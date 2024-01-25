package html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTMLGenerator {
	private static final Logger logger = LoggerFactory.getLogger(HTMLGenerator.class);
	private static final String FILE_PATH = "src/main/resources/templates/index.html";
	private static final int INDENTATION_LENGTH = 3;

	public static byte[] getHomeHTML(String userName) {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
				if (line.contains("name-space")) {
					String tmp = line.trim();
					int indent = line.length() - tmp.length() + INDENTATION_LENGTH;
                    sb.append(" ".repeat(Math.max(0, indent))); // 윗 줄과 3칸 indentation
					sb.append("<li><span class=\"navbar-text\" style=\"font-weight: 600;\">");
					sb.append(userName).append(" 님 안녕하세요").append("</span></li>").append("\n");
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sb.toString().getBytes();
	}
}
