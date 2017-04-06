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
	
	

	@Scheduled(fixedDelay = 10000)
    public void sendEnrollEmail() throws IOException {
		
		
		HashMap<String, String> mailContent = MailQueue.getMailContent();
		//	System.out.println("\nHello "+mailContent);
		for(String key: mailContent.keySet()){
			
			mailClient.sendEventMail(key,mailContent.get(key));
            mailContent.remove(key);
		}
    }

}
