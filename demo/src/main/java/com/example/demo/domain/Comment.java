package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "CommentTable")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(nullable = false, updatable = false, unique = true)
	private Long commentID = Long.valueOf(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post")
	private Post post;

	private String content = "";
	
	public Comment() {
		super();
<<<<<<< HEAD
	}
=======
		
		content = "";
	}

>>>>>>> 2dff22be6cc23f21524fee158724726104573249
	public Comment(User user, Post post, String content) {
		super();
		this.user = user;
		this.post = post;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Post getPost() {
		return post;
	}

	public User getUser() {
		return user;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
