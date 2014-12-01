package com.example.ithsblog;


public class JsonObjects {

	private int id;
	private String title;
	private String text;
	
	public JsonObjects(int id, String title, String text) {
		setId(id);
		setTitle(title);
		setText(text);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	
	 
}
