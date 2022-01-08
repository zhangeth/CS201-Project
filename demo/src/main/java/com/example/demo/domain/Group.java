package com.example.demo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "GroupTable")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(nullable = false, updatable = false, unique = true)
	private Long groupID = Long.valueOf(0);

	@ManyToMany(mappedBy = "groups")
	private Set<User> users;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event_group", orphanRemoval = true)
	@JsonIgnore
	private List<Event> events;

	private String name = "";

	public Group() {
		super();

		this.users = new HashSet<>();
		this.events = new ArrayList<>();
		this.name = "";
	}

	public Group(Set<User> users, String name) {
		super();
		this.users = users;
		this.events = new ArrayList<>();

		this.name = name;
	}

	public Group(String name) {
		super();
		this.users = new HashSet<>();
		this.events = new ArrayList<>();

		this.name = name;
	}

	public void addEvent(Event event) {
		this.events.add(event);
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public List<Event> getEvents() {
		return events;
	}

	public long getGroupID() {
		return groupID;
	}

	public String getName() {
		return name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void removeEvent(Event event) {
		this.events.remove(event);
	}

	public void removeUser(User user) {
		this.users.remove(user);
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}