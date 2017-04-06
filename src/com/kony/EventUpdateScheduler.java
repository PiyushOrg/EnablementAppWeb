package com.kony;


	
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Collection;
import java.util.Date;
import java.util.List;

	import org.apache.http.client.ClientProtocolException;
	import org.hibernate.HibernateException;
	import org.hibernate.Session;
	import org.hibernate.Transaction;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.scheduling.annotation.EnableScheduling;
	import org.springframework.scheduling.annotation.Scheduled;

	import com.kony.hibernate.Events;
	import com.kony.hibernate.Sessions;
	import com.kony.hibernate.Users;

	@Configuration
	@EnableScheduling
	public class EventUpdateScheduler extends Utils {

		@Scheduled(fixedDelay = 360000)
		public void sendNotifications() throws ClientProtocolException, IOException {
			Session session = null;

			try {
				session = factory.getCurrentSession();
				Transaction t = session.beginTransaction();
				List<Events> events = session.createCriteria(Events.class).list();
				// System.out.println("\nHi\n");
				for (Events event : events) {
					Collection<Sessions> sess = event.getSessions();
					if(sess != null){
						long sess_time = sess.iterator().next().getSession_st().getTime();//- (20 * 60 * 1000);
						Date session_time=new Date(sess_time);
						Date system_time = new Date(System.currentTimeMillis());
						//System.out.print("event "+event.getStatus());
						if(session_time.getYear() == system_time.getYear() && session_time.getMonth() == system_time.getMonth() && session_time.getDay() == system_time.getDay()){
							if(!event.getStatus().equals("Live")){
							     event.setStatus("Live");
							     t.commit();
							}   
						}
						else if(session_time.getYear() == system_time.getYear() && session_time.getMonth() == system_time.getMonth() && session_time.getDay() < system_time.getDay()){
							if(!event.getStatus().equals("Ended")){
							     event.setStatus("Ended");
							     t.commit();
							}   
						}
					}
				}	
				session.close();
			} catch (RuntimeException e) {
				if (session != null) {
					session.close();
				}

			}
		}
	}