package html;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class HTMLGenerator {
	private static final Logger logger = LoggerFactory.getLogger(HTMLGenerator.class);
	private static final String FILE_PATH = "src/main/resources/templates";
	private static final int INDENTATION_LENGTH = 3;

	public static byte[] getHomeHTML(String userName) {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH+"/index.html"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("login-button")) {
					line = line.replace("로그인", "로그아웃");
					line = line.replace("user/login.html", "#");

				}
				if (line.contains("navbar-name-space")) {
					int indent = getIndent(line);
					sb.append(" ".repeat(Math.max(0, indent))); // 윗 줄과 3칸 indentation
					sb.append("<li><span class=\"navbar-text\" style=\"font-weight: 600;\">");
					sb.append(userName).append(" 님 안녕하세요").append("</span></li>").append("\n");
				}
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sb.toString().getBytes();
	}

	private static int getIndent(String line) {
		String trimLine = line.trim();
        return line.length() - trimLine.length() + INDENTATION_LENGTH;
	}

	public static byte[] getUserListHTML() {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH+"/user/list.html"))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
				if (line.contains("user-table")) {
					sb.append("              <thead>").append("\n");
					sb.append("                <tr>").append("\n");
					sb.append("                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>").append("\n");
					sb.append("                </tr>").append("\n");
					sb.append("              </thead>").append("\n");
					sb.append("              <tbody>").append("\n");
					// 디비에서 읽으면서 사용자 리스트 뿌리기
					int count = 0;
					Collection<User> users = Database.findAll();
					for (User user : users) {
						sb.append("<tr>").append("\n");
						sb.append("<th scope=\"row\">").append(++count).append("</th");
						sb.append(" <td>").append(user.getUserId()).append("</td> <td>").append(user.getName()).append("</td> <td>").append(user.getEmail()).append("</td>");
						sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>").append("\n");
						sb.append("</tr>").append("\n");
					}
					sb.append("              </tbody>").append("\n");
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return sb.toString().getBytes();
	}

}
