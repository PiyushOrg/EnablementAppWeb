package com.kony.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @author kh2132
 *
 */
@Entity
public class Events {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	int event_id;
	String name;
	@Lob
	String description;
	
	byte image;
	String status;
	int max_score;
	String location;
	
	int averageRating;
	
	String event_type;
	
	ArrayList<String> likes = new ArrayList<String>();
	
	@ElementCollection
	@GenericGenerator(name = "sequence-gen", strategy ="sequence")
	@CollectionId(columns = {@Column(name = "session_id")}, generator = "sequence-gen", type = @Type(type="long"))
	Collection<Sessions> sessions = new ArrayList<Sessions>();
	
	@Column(columnDefinition = "LONGBLOB")
	HashMap<String, Integer> user_scores =  new HashMap<String, Integer>();
	@Column(columnDefinition = "LONGBLOB")
	HashMap<String, Integer> users_rank  =   new HashMap<String, Integer>();
	
	@Column(columnDefinition = "LONGBLOB")
	HashMap<String,Integer> ratingList   =   new HashMap<String, Integer>();
	
	public Events(){
		this.ratingList = new HashMap<String,Integer>();
		this.likes = new ArrayList<String>();
		this.user_scores = new HashMap<String, Integer>();
		
		this.users_rank = new HashMap<String, Integer>();
	}
	

	public int getEvent_id() {
		return event_id;
	}

	public void setEven_id(int event_id) {
		this.event_id = event_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<String> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}

	public int getMax_score() {
		return max_score;
	}

	public void setMax_score(int max_score) {
		this.max_score = max_score;
	}

	public byte getImage() {
		return image;
	}

	public void setImage(byte image) {
		this.image = image;
	}

	public Collection<Sessions> getSessions() {
		return sessions;
	}

	public void setSessions(Collection<Sessions> ses) {
		this.sessions = ses;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public HashMap<String, Integer> getRatingList() {
		return ratingList;
	}


	public void setRatingList(HashMap<String, Integer> ratingList) {
		this.ratingList = ratingList;
	}


	public int getAverageRating() {
		int count = 0;
		int sum = 0;
		for(int rate :getRatingList().values()){
			sum += rate;
			count++;
		}
		if (count == 0)
			return 0;
		return sum/count;
	}

	public void setAverageRating(int averageRating) {
		
		this.averageRating = averageRating;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public HashMap<String, Integer> getUser_scores() {
		return user_scores;
	}

	public void setUser_scores(HashMap<String, Integer> user_scores) {
		this.user_scores = user_scores;
	}

	public HashMap<String, Integer> getUsers_rank() {
		return users_rank;
	}

	public void setUsers_rank(HashMap<String, Integer> scoreMap) {
		
		TreeSet<Integer> sortedscore = new TreeSet<Integer>();
		Iterator<Entry<String, Integer>> it = scoreMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = it.next();
	       sortedscore.add((Integer) pair.getValue());
	    }
	   
	    int rank = sortedscore.size();
	   
	    HashMap<Integer, Integer> scoreRankMap = new HashMap<Integer, Integer>();
	   for(int i : sortedscore){
		   scoreRankMap.put(i, rank--);
	   }
	
	   HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
		for(String userid : scoreMap.keySet()){
			rankMap.put(userid,scoreRankMap.get(scoreMap.get(userid)));
		}
		
		this.users_rank = rankMap;
	}	
	
	public static void main(String args[]){
		HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
		scoreMap.put("m@piyushmittal.com", 1);
		scoreMap.put("m@piyushmittal1.com", 1);
		scoreMap.put("m@piyushmittal2.com", 2);
		scoreMap.put("m@piyushmittal3.com", 2);
		scoreMap.put("m@piyushmittal4.com", 33);
		scoreMap.put("m@piyushmittal5.com", 33);
		scoreMap.put("m@piyushmittal6.com", 11);
		scoreMap.put("m@piyushmittal7.com", 11);
		scoreMap.put("m@piyushmittal8.com", 21);
		scoreMap.put("m@piyushmittal9.com", 24);
		scoreMap.put("m@piyushmittal10.com", 40);
		scoreMap.put("m@piyushmittal11.com", 41);
		
		new Events().setUsers_rank(scoreMap);
		
	}
	
}
