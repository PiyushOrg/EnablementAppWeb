package com.kony;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kony.hibernate.Events;
import com.kony.hibernate.QRInfo;
import com.kony.hibernate.Sessions;

@Controller
public class EventController {
	
	
	 private static final SessionFactory sessionFactory = buildSessionFactory();

	    private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            return new Configuration().configure().buildSessionFactory();
	        }
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
	
	    SessionFactory factory;
	EventController() {
		factory = getSessionFactory();
	}

	@RequestMapping(value = "/createevent", method = RequestMethod.POST)

	public ModelAndView createEvent() {
		return new ModelAndView("createevent", "command", null);
	}

	@RequestMapping(value = "/editevent", method = RequestMethod.GET)
	public ModelAndView editpage() {
		return new ModelAndView("editevent", "command", null);
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public ModelAndView notifypage() {
		return new ModelAndView("notify", "command", null);
	}

	@RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
	public ModelAndView leaderboardpage() {
		return new ModelAndView("leaderboard", "command", null);
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String eventAdd(@ModelAttribute("SpringWeb") EventFormData eventFormData, ModelMap model) throws ParseException {
		System.out.println("Hello");
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		String event_name = eventFormData.getEvent_name();
		String event_description = eventFormData.getEvent_description();
		String event_location = eventFormData.getEvent_location();
		String event_type = eventFormData.getEvent_type();
		
		String session1_name = eventFormData.getSession1_name();
		String session1_desc = eventFormData.getSession1_description();
		String session1_st = eventFormData.getSession1_starttime();
		String session1_et = eventFormData.getSession1_endtime();
		String session1_points = eventFormData.getSession1_points();
		
		String session2_name = eventFormData.getSession2_name();
		String session2_desc = eventFormData.getSession2_description();
		String session2_st = eventFormData.getSession2_starttime();
		String session2_et = eventFormData.getSession2_endtime();
		String session2_points = eventFormData.getSession2_points();
		
		String session3_name = eventFormData.getSession3_name();
		String session3_desc = eventFormData.getSession3_description();
		String session3_st = eventFormData.getSession3_starttime();
		String session3_et = eventFormData.getSession3_endtime();
		String session3_points = eventFormData.getSession3_points();
		
		String session4_name = eventFormData.getSession4_name();
		String session4_desc = eventFormData.getSession4_description();
		String session4_st = eventFormData.getSession4_starttime();
		String session4_et = eventFormData.getSession4_endtime();
		String session4_points = eventFormData.getSession4_points();
		
		

		Events e1 = new Events();
		e1.setName(event_name);
		e1.setDescription(event_description);
		e1.setLocation(event_location);
		e1.setEvent_type(event_type);
		e1.setStatus("Upcoming");

		e1.setMax_score(50);
		System.out.println(event_name);
		System.out.println(event_description);
		System.out.println(event_location);
		System.out.println(session1_name);
		System.out.println(session1_st);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date date = sdf.parse(session1_st);
		
		System.out.println("Date is :"+date);
		

		Collection<Sessions> allses = new ArrayList<Sessions>();
		
		int total_score_event = 0;

		if (null != session1_name) {
			// Session 1 of event 1
			Sessions sess_e1_s1 = new Sessions();
			QRInfo qr = new QRInfo();

			sess_e1_s1.setSession_name(session1_name);
			sess_e1_s1.setSesion_description(session1_desc);
			sess_e1_s1.setSession_st(new java.sql.Date(sdf.parse(session1_st).getTime()));
			sess_e1_s1.setSession_et(new java.sql.Date(sdf.parse(session1_et).getTime()));
			sess_e1_s1.setScore(Integer.parseInt(session1_points));
			total_score_event += Integer.parseInt(session1_points);
			sess_e1_s1.setUsers_completed_session(new ArrayList<String>());
	
			qr.setEvent_name(event_name);
			qr.setSession_name(session1_name);
			qr.setImage();
			session.save(qr);
			

			allses.add(sess_e1_s1);
		}
		
		if (null != session2_name) {
			// Session 1 of event 1
			Sessions sess_e1_s2 = new Sessions();
			QRInfo qr = new QRInfo();

			sess_e1_s2.setSession_name(session2_name);
			sess_e1_s2.setSesion_description(session2_desc);
			sess_e1_s2.setSession_st(new java.sql.Date(sdf.parse(session2_st).getTime()));
			sess_e1_s2.setSession_et(new java.sql.Date(sdf.parse(session2_et).getTime()));
			sess_e1_s2.setScore(Integer.parseInt(session2_points));
			total_score_event += Integer.parseInt(session2_points);
			sess_e1_s2.setUsers_completed_session(new ArrayList<String>());
			
			qr.setEvent_name(event_name);
			qr.setSession_name(session2_name);
			qr.setImage();
			session.save(qr);
			

			allses.add(sess_e1_s2);
		}
		
		if (null != session3_name) {
			// Session 1 of event 1
			Sessions sess_e1_s3 = new Sessions();
			QRInfo qr = new QRInfo();

			sess_e1_s3.setSession_name(session3_name);
			sess_e1_s3.setSesion_description(session3_desc);
			sess_e1_s3.setSession_st(new java.sql.Date(sdf.parse(session3_st).getTime()));
			sess_e1_s3.setSession_et(new java.sql.Date(sdf.parse(session3_et).getTime()));
			sess_e1_s3.setScore(Integer.parseInt(session3_points));
			total_score_event += Integer.parseInt(session3_points);
			sess_e1_s3.setUsers_completed_session(new ArrayList<String>());
			
			qr.setEvent_name(event_name);
			qr.setSession_name(session3_name);
			qr.setImage();
			session.save(qr);

			allses.add(sess_e1_s3);
		}
		if (null != session4_name) {
			// Session 1 of event 1
			Sessions sess_e1_s4 = new Sessions();
			QRInfo qr = new QRInfo();

			sess_e1_s4.setSession_name(session4_name);
			sess_e1_s4.setSesion_description(session4_desc);
			sess_e1_s4.setSession_st(new java.sql.Date(sdf.parse(session4_st).getTime()));
			sess_e1_s4.setSession_et(new java.sql.Date(sdf.parse(session4_et).getTime()));
			sess_e1_s4.setScore(Integer.parseInt(session4_points));
			total_score_event += Integer.parseInt(session4_points);
			sess_e1_s4.setUsers_completed_session(new ArrayList<String>());
			
			qr.setEvent_name(event_name);
			qr.setSession_name(session4_name);
			qr.setImage();
			session.save(qr);

			allses.add(sess_e1_s4);
		}
		
		e1.setMax_score(total_score_event);
		//e1.setLikes(new ArrayList<String>());
		e1.setSessions(allses);
		session.save(e1);//persisting the object 
		
		   t.commit();
		session.close();
		

		return "createevent";
	}

	@RequestMapping(value = "/editEvent", method = RequestMethod.POST)
	public String eventEdit(@ModelAttribute("SpringWeb") EventFormData eventFormData, ModelMap model) {

//		String event_id = eventFormData.getEvent_id();
//		String event_name = eventFormData.getEvent_name();
//		String event_description = eventFormData.getEvent_description();
//		String event_location = eventFormData.event_location;
//
//		String session1_name = eventFormData.getSession1_name();
//
//		Events e1 = session.get(Events.class, event_id);
//
//		e1.setName(event_name);
//		e1.setDescription(event_description);
//		e1.setLocation(event_location);
//
//		e1.setMax_score(50);
//
//		Collection<Sessions> allses = new ArrayList<Sessions>();
//
//		if (null != session1_name) {
//			// Session 1 of event 1
//			Sessions sess_e1_s1 = new Sessions();
//
//			sess_e1_s1.setSession_name("Kony Vizualiser first session");
//			sess_e1_s1.setSesion_description("Demo of installation steps");
//			Date st = new Date(1212312312);
//			Date et = new Date(1212312342);
//			//sess_e1_s1.setSession_st(st);
//			//sess_e1_s1.setSession_et(et);
//
//			ArrayList<String> users_completed_session = sess_e1_s1.getUsers_completed_session();
//			users_completed_session.add("piyush.mittal@kony.com");
//			users_completed_session.add("karan@kony.com");
//			allses.add(sess_e1_s1);
//		}
//
//		e1.setSessions(allses);
//		t.commit();
//
		return "createevent";
	}

}
