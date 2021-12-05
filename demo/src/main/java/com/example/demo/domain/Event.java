package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "EventTable")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventID;

	// private Ordered_Map<Integer, Bookmark> proposedRestaurants

	private String startTime, endTime;
	private boolean closed;
//
//	@Autowired
//	private EventRepository eventRepo;

	public Event() {
	}

	public Event(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// getters/setters
	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public long getEventID() {
		return eventID;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean getClosed() {
		return closed;
	}
}
