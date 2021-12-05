package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "ImageTable")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long imageID;
	private long userID;

	private String url;

//	@Autowired
//	private ImageRepository imageRepo;

	public Image(long userID, String url) {
		this.userID = userID;
		this.url = url;
	}

	void setImageID(long imageID) {
		this.imageID = imageID;
	}

	long getImageID() {
		return imageID;
	}

	void setUserID(long userID) {
		this.userID = userID;
	}

	long getUserID() {
		return userID;
	}

	void setURL(String url) {
		this.url = url;
	}

	String getURL() {
		return url;
	}
}
