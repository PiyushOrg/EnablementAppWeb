package com.kony;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.kony.hibernate.Events;
import com.kony.hibernate.Sessions;
import com.kony.hibernate.Users;

//@Configuration
//@EnableScheduling
public class NotificationScheduler extends Utils {
	
	@Scheduled(fixedDelay = 60000)
    public void sendNotifications() throws ClientProtocolException, IOException {
		Session session = factory.getCurrentSession();
		Transaction t = session.beginTransaction();
		List<Users> users = session.createCriteria(Users.class).list();
		//System.out.println("\nHi\n");
		for(Users user : users){
			ArrayList<Integer> myevents = user.getMyevents();
			for(int event_id : myevents){
				Events event = session.get(Events.class, event_id);
				Collection<Sessions> sessions = event.getSessions();
				 for(Sessions sess : sessions){
					// System.out.println("Session time "+sess.getSession_st().getTime());
					 if(sess.getSession_st().getTime() - (20*60*1000) == System.currentTimeMillis()){
						 
						 ArrayList<String> ksids = sess.getUsers_completed_session();
						 new ScheduledTasks();
						ScheduledTasks.triggerPush(ksids, "Session "+sess.getSession_name()+" is going to start in couple of minutes.");
					 }
				 }
			}
		}
		session.close();
    }
}