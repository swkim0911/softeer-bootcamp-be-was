package model;

import dto.BoardDto;

import java.time.LocalDate;
import java.util.Map;

public class Board {
	private Long boardId;
	private String writer;
	private String title;
	private String contents;
	private LocalDate createdDate;

	public static Board create(BoardDto boardDto) {
		return new Board(boardDto.getBoardId(), boardDto.getWriter(), boardDto.getTitle(), boardDto.getContents(), boardDto.getCreatedDate());
	}

	private Board(Long boardId, String writer, String title, String contents, LocalDate createdDate) {
		this.boardId = boardId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
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

	@Override
	public String toString() {
		return "Board [boardId=" + boardId +", writer=" + writer + ", title=" + title + ", contents=" + contents + ", createdDate=" + createdDate + "]";
	}
}
