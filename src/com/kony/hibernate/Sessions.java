package com.kony.hibernate;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import com.kony.QRcodeUtils;
@Embeddable
public class Sessions {
	
	
	
	String session_name;
	@Lob
	String sesion_description;
	Timestamp session_st;
	Timestamp session_et;
	
	
	
	int score;
	
	@Column(columnDefinition = "LONGBLOB") 
	ArrayList<String> users_completed_session = new ArrayList<String>();
	
	@Column(columnDefinition = "LONGBLOB") 
	byte[] image;
	
	
	public String getSession_name() {
		return session_name;
	}
	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}
	public String getSesion_description() {
		return sesion_description;
	}
	public void setSesion_description(String sesion_description) {
		this.sesion_description = sesion_description;
	}
	public Timestamp getSession_st() {
		return session_st;
	}
	public void setSession_st(Date session_st) {
		this.session_st = new java.sql.Timestamp(session_st.getTime());
	}
	public Timestamp getSession_et() {
		return session_et;
	}
	public void setSession_et(Date session_et) {
		this.session_et = new java.sql.Timestamp(session_et.getTime());
	}
	public ArrayList<String> getUsers_completed_session() {
		return users_completed_session;
	}
	public void setUsers_completed_session(ArrayList<String> users_completed_session) {
		this.users_completed_session = users_completed_session;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(String eventId) {
		this.image = new QRcodeUtils().getQRImage(eventId+","+getSession_name()+","+System.currentTimeMillis());
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean timeValidation(String attendenceTimeStr) throws ParseException{
		return true;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
//		Date attendenceTime = (Date) formatter.parse(attendenceTimeStr);
//		if(attendenceTime.getTime() >= session_st.getTime() && attendenceTime.getTime() <= session_st.getTime() )
//			return true;
//		else return false;
	}
	
}
