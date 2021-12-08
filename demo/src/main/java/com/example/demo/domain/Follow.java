package com.example.demo.domain;

import javax.persistence.*;
import java.util.*;
import java.util.Date; 
import java.util.Calendar; 

@Entity
public class Follow {
	@Id
	@Column(nullable = false, unique = true)
	private Long follow_id;
	@Column(nullable = false, unique = false)
	public Long userID;
	
	public Follow() {
		follow_id = System.currentTimeMillis();
		userID = (long) 0;
	}
    public Follow(Long userID)
    {
    	follow_id = System.currentTimeMillis();
        this.userID = userID;
    }
}
