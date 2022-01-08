package com.example.demo.domain;

import java.time.LocalDateTime;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EventTable")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(nullable = false, updatable = false, unique = true)
	private Long eventID = Long.valueOf(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant")
	@JsonIgnore
	private Restaurant restaurant;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime, endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_group")
	private Group event_group;

	public Event() {
		super();

		this.event_group = new Group();
	}

	public Event(LocalDateTime startTime, LocalDateTime endTime, Group group, Restaurant restaurant) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.event_group = group;
		this.restaurant = restaurant;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public Group getEvent_group() {
		return event_group;
	}

	public Long getEventID() {
		return eventID;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setEvent_group(Group event_group) {
		this.event_group = event_group;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
}