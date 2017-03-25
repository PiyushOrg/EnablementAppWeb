package com.kony;

import java.io.IOException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MailScheduler{
	
	@Autowired
	MailClient mailClient;
	
	private static MailScheduler mailScheduler = new MailScheduler();
	private static HashMap<String, String> mailContent1 = new HashMap<String, String>();
   
   
   
 //Get the only object available
   public static MailScheduler getInstance(){
      return mailScheduler;
   }

   void add(String email, String eventname){
	   mailContent1.put(email, eventname);
   }

	@Scheduled(fixedDelay = 10000)
    public void sendEnrollEmail() throws IOException {
		MailQueue.getInstance();
		//System.out.println("\nHello "+MailQueue.getInstance().getMailContent());
		HashMap<String, String> mailContent = MailQueue.getMailContent();
		//mailContent.put("piyush.mittal@kony.com", "Testing");
		
		for(String key: mailContent.keySet()){
			
			mailClient.sendEventMail(key,mailContent.get(key));
            mailContent.remove(key);
		}
    }

}
