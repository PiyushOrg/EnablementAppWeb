package com.kony.hibernate;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class My_Events {
	@Id
	String userid;

	ArrayList<Integer> event_ids = new ArrayList<Integer>();

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public ArrayList<Integer> getEvent_ids() {
		return event_ids;
	}

	public void setEvent_ids(ArrayList<Integer> event_ids) {
		this.event_ids = event_ids;
	}

	

}
