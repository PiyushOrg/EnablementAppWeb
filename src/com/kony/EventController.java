package com.kony;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kony.hibernate.Events;
import com.kony.hibernate.Sessions;

@Controller
public class EventController {
	Transaction t = null;
	Session session = null;

	EventController() {
		Configuration cfg = new Configuration();
		cfg.configure();
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();
		t = session.beginTransaction();
	}

	@RequestMapping(value = "/createevent", method = RequestMethod.GET)

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

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public ModelAndView sample() {
		// System.out.println(postPayload);
		return new ModelAndView("sample", "command", new SampleData());
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String eventAdd(@ModelAttribute("SpringWeb") EventFormData eventFormData, ModelMap model) {
		String event_name = eventFormData.getEvent_name();
		String event_description = eventFormData.getEvent_description();
		String event_location = eventFormData.event_location;

		String session1_name = eventFormData.getSession1_name();

		Events e1 = new Events();
		e1.setName(event_name);
		e1.setDescription(event_description);
		e1.setLocation(event_location);

		e1.setMax_score(50);
		System.out.println(eventFormData.getEvent_name());
		System.out.println(eventFormData.getEvent_location());

		Collection<Sessions> allses = new ArrayList<Sessions>();

		if (null != session1_name) {
			// Session 1 of event 1
			Sessions sess_e1_s1 = new Sessions();

			sess_e1_s1.setSession_name("Kony Vizualiser first session");
			sess_e1_s1.setSesion_description("Demo of installation steps");
			Date st = new Date(1212312312);
			Date et = new Date(1212312342);
			sess_e1_s1.setSession_st(st);
			sess_e1_s1.setSession_et(et);

			ArrayList<String> users_completed_session = sess_e1_s1.getUsers_completed_session();
			users_completed_session.add("piyush.mittal@kony.com");
			users_completed_session.add("karan@kony.com");
			allses.add(sess_e1_s1);
		}

		e1.setSessions(allses);
		t.commit();

		return "createevent";
	}

	@RequestMapping(value = "/editEvent", method = RequestMethod.POST)
	public String eventEdit(@ModelAttribute("SpringWeb") EventFormData eventFormData, ModelMap model) {

		String event_id = eventFormData.getEvent_id();
		String event_name = eventFormData.getEvent_name();
		String event_description = eventFormData.getEvent_description();
		String event_location = eventFormData.event_location;

		String session1_name = eventFormData.getSession1_name();

		Events e1 = session.get(Events.class, event_id);

		e1.setName(event_name);
		e1.setDescription(event_description);
		e1.setLocation(event_location);

		e1.setMax_score(50);

		Collection<Sessions> allses = new ArrayList<Sessions>();

		if (null != session1_name) {
			// Session 1 of event 1
			Sessions sess_e1_s1 = new Sessions();

			sess_e1_s1.setSession_name("Kony Vizualiser first session");
			sess_e1_s1.setSesion_description("Demo of installation steps");
			Date st = new Date(1212312312);
			Date et = new Date(1212312342);
			sess_e1_s1.setSession_st(st);
			sess_e1_s1.setSession_et(et);

			ArrayList<String> users_completed_session = sess_e1_s1.getUsers_completed_session();
			users_completed_session.add("piyush.mittal@kony.com");
			users_completed_session.add("karan@kony.com");
			allses.add(sess_e1_s1);
		}

		e1.setSessions(allses);
		t.commit();

		return "createevent";
	}

	@RequestMapping(value = "/sampledataadded", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("SpringWeb") Student student, ModelMap model) {
		String name = student.getName();
		System.out.println(student.getName());
		// model.addAttribute("name", student.getName());
		// model.addAttribute("age", student.getAge());
		// model.addAttribute("id", student.getId());
		//
		// return "";
		return "createevent";
	}
}
