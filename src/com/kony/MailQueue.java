package com.kony;

import java.util.HashMap;

public class MailQueue {
	
	private static MailQueue mailQueue = new MailQueue();
	private static HashMap<String, String> mailContent = new HashMap<String, String>();
	private MailQueue(){
		
	}
	
	//Get the only object available
	   public static MailQueue getInstance(){
	      return mailQueue;
	   }
	   
	   void add(String email, String eventname){
		   mailContent.put(email, eventname);
	   }

	public static HashMap<String, String> getMailContent() {
		return mailContent;
	}
	   
	   

}
