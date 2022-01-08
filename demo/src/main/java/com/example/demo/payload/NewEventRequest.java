package com.example.demo.payload;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NewEventRequest {
	@NotBlank
	private String location;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;

	@NotNull
	private Long groupID;

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public Long getGroupID() {
		return groupID;
	}

	public String getLocation() {
		return location;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
}
