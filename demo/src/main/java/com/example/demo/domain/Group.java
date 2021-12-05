package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "GroupTable")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long groupID;

	private String name;
//
//	@Autowired
//	private GroupRepository groupRepo;

	public Group() {

	}

	public Group(User owner, String name) {
		this.name = name;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
