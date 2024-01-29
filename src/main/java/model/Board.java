package model;

import java.time.LocalDate;

public class Board {
	private String boardId;
	private String writer;
	private String title;
	private String contents;
	private LocalDate createdDate;

	public String getBoardId() {
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
