package com.pcwk.ehr.cmn;

public class Message extends DTO {

	
	private int messagId;       //1,0
	private String messageContents;//메시지 내용
	
	
	public Message() {

	}


	public Message(int messagId, String messageContents) {
		super();
		this.messagId = messagId;
		this.messageContents = messageContents;
	}


	public int getMessagId() {
		return messagId;
	}


	public void setMessagId(int messagId) {
		this.messagId = messagId;
	}


	public String getMessageContents() {
		return messageContents;
	}


	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}


	@Override
	public String toString() {
		return "Message [messagId=" + messagId + ", messageContents=" + messageContents + "]";
	}
	
	

	
	
	
}
