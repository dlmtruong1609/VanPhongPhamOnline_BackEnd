package com.nhom4.vanphongphamonline.models;

public class EmailContent {
	private String emailTo;
	private String subject;
	private String content;
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public EmailContent(String emailTo, String subject, String content) {
		super();
		this.emailTo = emailTo;
		this.subject = subject;
		this.content = content;
	}
	
}
