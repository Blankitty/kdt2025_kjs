package boardapp.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
	private static int count = 1;
	
	private int id;
	private String title;
	private String content;
	private String writer;
	private String date;
	private int views;
	
	public Board(String title, String content, String writer) {
		super();
		this.id = count++;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.date = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.views = 0;
	}
	
	
	public static int getCount() {
		return count;
	}


	public static void setCount(int count) {
		Board.count = count;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getViews() {
		return views;
	}
	
	public void setViews(int views) {
		this.views = views;
	}


	
	public void increaseViews() {
		this.views++;
	}




	

}
