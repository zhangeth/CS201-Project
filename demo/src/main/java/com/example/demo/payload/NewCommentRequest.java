package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewCommentRequest {
	@NotBlank
	private String comment;

	@NotNull
	private Long postID;

	public String getComment() {
		return comment;
	}

	public Long getPostID() {
		return postID;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPostID(Long postID) {
		this.postID = postID;
	}
}
