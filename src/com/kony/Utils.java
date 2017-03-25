package com.kony;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kony.hibernate.Events;
import com.kony.hibernate.Feedbacks;
import com.kony.hibernate.QRInfo;
import com.kony.hibernate.Sessions;
import com.kony.hibernate.Users;

@Path("/data")
public class Utils {

	MailClient mc = null;

	HashMap<String, String> mailContent;

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	SessionFactory factory;

	public Utils() {
		// Configuration cfg = new Configuration();
		// cfg.configure();
		// this.factory = cfg.buildSessionFactory();
		// SessionFactory
		factory = getSessionFactory();
		// session = factory.openSession();
		// t = session.beginTransaction();
		// mc = new MailClient();
		this.mailContent = new HashMap<String, String>();
		// mailContent.put("piyush.mittal@kony.com", "Bhai");
		// System.out.println("\nUtils initialized\n");
	}

	@GET
	@Path("/register_user/{email}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUsers(@PathParam("email") String email, @PathParam("name") String name) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		Users user = session.get(Users.class, email);
		if (user != null) {
			session.close();
			return Response.status(200).entity("{\"message\":\"User Already Exist\"}").build();
		} else {
			user = new Users();// (Users) context.getBean("users");
			user.setEmail(email.toLowerCase());
			user.setName(name);
			session.save(user);

			t.commit();
			session.close();

		}
		return Response.status(200).entity("{\"message\":\"User Added Successfully\"}").build();
	}

	@GET
	@Path("/enroll/{eventid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response enroll(@PathParam("eventid") Integer eventid, @PathParam("userid") String userid)
			throws IOException {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		userid = userid.toLowerCase();
		Users user = session.get(Users.class, userid);
		if (user.getMyevents().contains(eventid)) {
			// mc.sendEventMail("event.getName()",userid);
			session.close();
			// System.out.println("\nInside method\n");
			// MailQueue.getInstance().add(userid, "Testing");
			// this.mailContent.put(userid, "event.getName()");
			// System.out.println("\nInside method\n"+mailContent);
			// mailContent.
			return Response.status(200).entity("{\"message\":\"Already enrolled.\"}").build();
		}
		user.getMyevents().add(eventid);
		Events event = session.get(Events.class, eventid);
		event.getUser_scores().put(userid, 0);
		event.getUsers_rank().put(userid, 0);
		MailQueue.getInstance().add(userid, event.getName());

		t.commit();
		session.close();
		// mc.sendEventMail(event.getName(),userid);
		return Response.status(200).entity("{\"message\":\"Enrolled successfully.\"}").build();
	}

	@GET
	@Path("/my_events/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response myevents(@PathParam("userid") String userid) throws JsonProcessingException {
		Session session = factory.openSession();
		session.beginTransaction();

		userid = userid.toLowerCase();
		ObjectMapper mapper = new ObjectMapper();

		Users userdata = session.get(Users.class, userid);

		ArrayList<Integer> event_ids = userdata.getMyevents();
		Collections.reverse(event_ids);
		Collection<Events> events = new ArrayList<Events>();
		for (Integer i : event_ids) {
			events.add(session.get(Events.class, i));
		}

		String output = mapper.writeValueAsString(events);

		session.close();
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/show_events/{filter}")
	@Produces(MediaType.APPLICATION_JSON)

	public Response showEvents(@PathParam("filter") String filter) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Session session = factory.openSession();
		session.beginTransaction();

		List<Events> events = null;

		if (filter.equals("all")) {
			events = session.createCriteria(Events.class).addOrder(Order.desc("event_id")).list();
		} else if (filter.equals("Hackathon"))
			events = session.createQuery("from Events where event_type = 'Hackathon'").list();
		else if (filter.equals("FeatureOverview"))
			events = session.createQuery("from Events where event_type = 'FeatureOverview'").list();
		else if (filter.equals("TechnicalDeepdive"))
			events = session.createQuery("from Events where event_type = 'TechnicalDeepdive'").list();
		else if (filter.equals("Training"))
			events = session.createQuery("from Events where event_type = 'Training'").list();

		String output = mapper.writeValueAsString(events);

		session.close();
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/startNotifications")
	public void sendBulkNotification() throws InterruptedException {
		Timer time = new Timer(); // Instantiate Timer Object
		ScheduledTasks st = new ScheduledTasks(); // Instantiate SheduledTask
													// class
		time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs

		// for demo only.
		for (int i = 0; i <= 5; i++) {
			System.out.println("Execution in Main Thread...." + i);
			Thread.sleep(2000);
			if (i == 5) {
				System.out.println("Application Terminates");
				System.exit(0);
			}
		}

	}

	@GET
	@Path("/register_ksid/{userid}/{ksid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register_ksid(@PathParam("userid") String userid, @PathParam("ksid") String ksid)
			throws JsonProcessingException {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		userid = userid.toLowerCase();
		Users user = session.get(Users.class, userid);
		if (user.getKSID().contains(ksid)) {
			session.close();
			return Response.status(200).entity("{\"message\":\"ksid already exist\"}").build();
		}
		user.getKSID().add(ksid);
		// if(!t.isActive())
		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"Added Successfully\"}").build();
	}

	@POST
	@Path("/feedback")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response submitFeedback(@FormParam("eventid") Integer eventId, @FormParam("userid") String userId,
			@FormParam("feedbackdata") String feedback) throws JsonProcessingException {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		Feedbacks fb = new Feedbacks();
		fb.setUserId(userId);
		fb.setEventId(eventId);
		fb.setFeedback(feedback);

		session.save(fb);

		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"Feedback added successfully\"}").build();
	}

	@POST
	@Path("/profilepic")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response profilePic(@FormParam("access_token") String access_token) throws JsonProcessingException {
		//https: // graph.microsoft.com/v1.0/me/photo/$value
		try {

			URL url = new URL("https://graph.microsoft.com/v1.0/me/photo/$value");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "image/jpeg");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			InputStream is = null;
			if (conn.getResponseCode() != 200) {
				is = conn.getErrorStream();
			} else {
				is = conn.getInputStream();
			}

			byte[] bytes = IOUtils.toByteArray(is);
			byte[] encoded = java.util.Base64.getEncoder().encode(bytes);

			conn.disconnect();
			return Response.status(200).entity("{\"message\":\"" + new String(encoded) + "\"}").build();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return Response.status(200).entity("{\"message\":\"image not found\"}").build();
	}

	@GET
	@Path("/showFeedbacks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showFeedbacks() throws ClientProtocolException, IOException {
		Session session = factory.openSession();
		session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		List<Feedbacks> feedbacks = session.createCriteria(Feedbacks.class).list();
		session.close();
		return Response.status(200).entity(mapper.writeValueAsString(feedbacks)).build();
	}

	@GET
	@Path("/sendNotificationsAll/{message}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendNotificationsAll(@PathParam("message") String message) throws ClientProtocolException, IOException {
		Session session = factory.openSession();
		session.beginTransaction();
		List<Users> users = session.createCriteria(Users.class).list();
		ArrayList<String> ksids = new ArrayList<String>();
		for (int i = 0; i < users.size(); i++) {
			ksids.addAll(users.get(i).getKSID());
		}

		new ScheduledTasks();
		ScheduledTasks.triggerPush(ksids,message);
		session.close();
		return Response.status(200).entity("{\"message\":\"sent Successfully\"}").build();
	}

	@GET
	@Path("/sendMail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMail() throws ClientProtocolException, IOException {
		new MailClient().sendEventMail("piyush.mittal@kony.com", "Testing");
		return Response.status(200).entity("{\"message\":\"sent Successfully\"}").build();
	}

	@GET
	@Path("/rating/{eventid}/{userid}/{rate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rating(@PathParam("eventid") Integer eventid, @PathParam("userid") String userId,
			@PathParam("rate") Integer rate) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		userId = userId.toLowerCase();
		Events event = session.get(Events.class, eventid);
		event.getRatingList().put(userId, rate);

		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"event rating success \"}").build();
	}

	@GET
	@Path("/allusers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allUsers() throws JsonProcessingException {
		Session session = factory.openSession();
		session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		List<Users> users = session.createCriteria(Users.class).list();
		String output = mapper.writeValueAsString(users);
		session.close();
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/userinfo/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userInfo(@PathParam("userid") String userid) throws JsonProcessingException {
		Session session = factory.openSession();
		session.beginTransaction();
		userid = userid.toLowerCase();
		ObjectMapper mapper = new ObjectMapper();
		Users user = session.get(Users.class, userid);
		String output = mapper.writeValueAsString(user);
		session.close();
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/likes/{eventid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response likes(@PathParam("eventid") Integer eventid, @PathParam("userid") String userid) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		userid = userid.toLowerCase();
		Events event = session.get(Events.class, eventid);
		event.getLikes().add(userid);
		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"like success \"}").build();
	}

	@GET
	@Path("/qrimage/{eventname}/{sessionname}")
	@Produces("image/png")
	public Response QRImage(@PathParam("eventname") String eventname, @PathParam("sessionname") String session_name)
			throws IOException {
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria cr = session.createCriteria(QRInfo.class);
		cr.add(Restrictions.eq("event_name", eventname));
		cr.add(Restrictions.eq("session_name", session_name));
		List<QRInfo> qrinfo = cr.list();

		InputStream in = new ByteArrayInputStream(qrinfo.get(0).getImage());
		BufferedImage bImageFromConvert = ImageIO.read(in);
		session.close();
		return Response.ok(bImageFromConvert).build();

		// return Response.ok(null).build();
	}

	@POST
	@Path("/attendance")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response attendance(@FormParam("encrypted_data") String encrypted_data) throws ParseException {

		String data[] = new Encryptor().QRParser(encrypted_data);
		String eventname = data[0];
		String session_name = data[1];
		String userid = data[3];
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		userid = userid.toLowerCase();
		Criteria cr = session.createCriteria(Events.class);
		cr.add(Restrictions.eq("name", eventname));
		List<Events> events = cr.list();
		if (events.size() == 0) {
			return Response.status(200).entity("{\"message\":\"Invalid QR code\"}").build();
		}
		for (Sessions sess : events.get(0).getSessions()) {
			if (sess.getSession_name().equals(session_name)) {
				if (sess.getUsers_completed_session().contains(userid)) {
					session.close();
					return Response.status(200).entity("{\"message\":\"Already present\"}").build();
				}
				if (true) {
					// sess.timeValidation(timestamp)){
					int sess_score = sess.getScore();
					HashMap<String, Integer> map = events.get(0).getUser_scores();
					if (null != map.get(userid)) {
						int score = map.get(userid);
						map.put(userid, score + sess_score);
					} else
						map.put(userid, sess_score);

					Users user = session.get(Users.class, userid);
					user.setCumulative_score(user.getCumulative_score() + sess_score);

					// If user not enrolled yet
					int event_id = events.get(0).getEvent_id();
					if (!user.getMyevents().contains(event_id)) {
						user.getMyevents().add(event_id);
						MailQueue.getInstance().add(userid, events.get(0).getName());
					}

					sess.getUsers_completed_session().add(userid);

				}
				break;
			}
		}

		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"Presence marked successfully\"}").build();
	}

	@GET
	@Path("/refreshranks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refreshRanks() {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		// Events rank update
		List<Events> events = session.createCriteria(Events.class).list();

		for (Events event : events) {
			HashMap<String, Integer> scoreMap = event.getUser_scores();
			event.setUsers_rank(scoreMap);
		}

		// Users rank update
		List<Users> users = session.createCriteria(Users.class).list();

		HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
		for (Users user : users) {
			scoreMap.put(user.getEmail(), user.getCumulative_score());
		}

		Iterator<Entry<String, Integer>> it = scoreMap.entrySet().iterator();
		TreeSet<Integer> sortedscore = new TreeSet<Integer>();
		while (it.hasNext()) {
			Map.Entry pair = it.next();
			sortedscore.add((Integer) pair.getValue());
		}

		int rank = sortedscore.size();

		HashMap<Integer, Integer> scoreRankMap = new HashMap<Integer, Integer>();
		for (int i : sortedscore) {
			scoreRankMap.put(i, rank--);
		}

		HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
		for (String userid : scoreMap.keySet()) {
			rankMap.put(userid, scoreRankMap.get(scoreMap.get(userid)));
		}

		for (Users user : users) {
			String userId = user.getEmail();
			session.get(Users.class, userId).setRank(rankMap.get(userId));

		}
		t.commit();
		session.close();
		return Response.status(200).entity("{\"message\":\"User and events rank updated\"}").build();
	}

}
