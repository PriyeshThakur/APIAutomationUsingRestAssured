package com.APIAutomation;

public class postReqPayloadUsers {
	private String id;
	private String createdAt;
	
	public postReqPayloadUsers(String id, String createdAt) {
		this.id = id;
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "postReqPayloadUsers [id=" + id + ", createdAt=" + createdAt + "]";
	}

}
