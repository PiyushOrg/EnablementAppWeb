package com.kony.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kony.Encryptor;
import com.kony.QRcodeUtils;

@Entity
public class QRInfo {
	
//	String key = "Kony9999Kony2347"; // 128 bit key
//    String initVector = "RandomInitVector"; // 16 bytes IV
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	int qr_id;
	
	String event_name;
	String session_name;
	
	@Column(columnDefinition = "LONGBLOB") 
	byte[] image;

	public int getQr_id() {
		return qr_id;
	}

	public void setQr_id(int qr_id) {
		this.qr_id = qr_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getSession_name() {
		return session_name;
	}

	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage() {
		String data = getEvent_name()+","+getSession_name()+","+System.currentTimeMillis();
		
		new Encryptor();
		this.image  = new QRcodeUtils().getQRImage(Encryptor.encrypt("Kony9999Kony2347", "RandomInitVector", data));
	}
	
}
