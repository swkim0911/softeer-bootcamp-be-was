package dto;

import java.time.LocalDate;
import java.util.Map;

public class BoardDto {

	private static Long boardSequence = 0L;
	private Long boardId;
	private String writer;
	private String title;
	private String contents;
	private LocalDate createdDate;

	public BoardDto(Map<String, String> queryParameters, LocalDate createdDate) {
		this.boardId = ++boardSequence;
		this.writer = queryParameters.get("writer");
		this.title = queryParameters.get("title");
		this.contents = queryParameters.get("contents");
		this.createdDate = createdDate;
	}

	public static Long getBoardSequence() {
		return boardSequence;
	}

	public Long getBoardId() {
		return boardId;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}
}
