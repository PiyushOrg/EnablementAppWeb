package com.kony.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Feedbacks {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	int feedback_id;
	
	int eventId;
	String userId;
	@Lob
	String feedback;
	
	
	public int getFeedback_id() {
		return feedback_id;
	}


	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}


	public int getEventId() {
		return eventId;
	}


	public void setEventId(int eventId) {
		this.eventId = eventId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFeedback() {
		return feedback;
	}


	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	public static void main(String args[]){
		//HashMap<Integer, HashMap<String, ArrayList<String>>> obj =  new Feedbacks().setFeedback();
		
		//System.out.println(obj);
	}
}
