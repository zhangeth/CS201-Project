package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "BookmarkTable")
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookmarkID;

	private long userID;
	private long restaurantID;
	private String noteText;

//	@Autowired
//	private BookmarkRepository bookmarkRepo;

	public Bookmark() {
	}

	public Bookmark(long userID, long restaurantID, String noteText) {
		this.userID = userID;
		this.restaurantID = restaurantID;
		this.noteText = noteText;
	}

	// getters/setters
	public void setBookmarkID(long bookmarkID) {
		this.bookmarkID = bookmarkID;
	}

	public long getBookmarkID() {
		return bookmarkID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getUserID() {
		return userID;
	}

	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public long getRestaurantID() {
		return restaurantID;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

	public String getNoteText() {
		return noteText;
	}

}
