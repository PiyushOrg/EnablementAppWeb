package com.kony.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

//@Component
@Entity
public class Users {
	@Id
	String email;
	String name;
	int cumulative_score;
	int rank;
	byte[] image;
	
	ArrayList<String> KSID;
	
	ArrayList<Integer> myevents;
	
	public Users(){
		this.KSID = new ArrayList<String>();
		
		this.myevents = new ArrayList<Integer>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}

	public int getCumulative_score() {
		return cumulative_score;
	}

	public void setCumulative_score(int cumulative_score) {
		this.cumulative_score = cumulative_score;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public ArrayList<Integer> getMyevents() {
		return myevents;
	}

	public void setMyevents(ArrayList<Integer> myevents) {
		this.myevents = myevents;
	}

	public ArrayList<String> getKSID() {
		return KSID;
	}

	public void setKSID(ArrayList<String> kSID) {
		KSID = kSID;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
