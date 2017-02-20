package com.kony;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kony.hibernate.Events;
import com.kony.hibernate.Sessions;
import com.kony.hibernate.Users;



@Path("/data")
public class Utils {
	Transaction t = null;
	Session session = null;

	public Utils() {
		Configuration cfg = new Configuration();
		cfg.configure();
		SessionFactory factory = cfg.buildSessionFactory();
		session = factory.openSession();
		t = session.beginTransaction();

	}

	@GET
	@Path("/register_user/{email}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUsers(@PathParam("email") String email, @PathParam("name") String name) {
		Users user = session.get(Users.class, email);
		if (user != null)
			return Response.status(200).entity("{\"message\":\"User Already Exist\"}").build();
		else {

			//ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			user = new Users();// (Users) context.getBean("users");
			user.setEmail(email.toLowerCase());
			user.setName(name);
			session.save(user);
			t.commit();
		}
		return Response.status(200).entity("{\"message\":\"User Added Successfully\"}").build();
	}

	@GET
	@Path("/create_events")
	public void createEvent(ArrayList<String> events, ArrayList<ArrayList<String>> sessions) {
	}

	@GET
	@Path("/enroll/{eventid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response enroll(@PathParam("eventid") Integer eventid, @PathParam("userid") String userid) {
		Users user = session.get(Users.class, userid);
		if (user.getMyevents().contains(eventid)){
			Events event = session.get(Events.class, eventid);
			event.getUser_scores().put(userid, 0);
			event.getUsers_rank().put(userid, 0);
			t.commit();
			return Response.status(200).entity("{\"message\":\"Already exist\"}").build();
		}
		user.getMyevents().add(eventid);
		session.get(Events.class, eventid).getUser_scores().put(userid, 0);
		t.commit();
		refreshRanks();
		return Response.status(200).entity("{\"message\":\"Added Successfully\"}").build();
	}

	@GET
	@Path("/my_events/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response myevents(@PathParam("userid") String userid) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Users userdata = session.get(Users.class, userid);

		ArrayList<Integer> event_ids = userdata.getMyevents();

		Collection<Events> events = new ArrayList<Events>();
		for (Integer i : event_ids) {
			events.add(session.get(Events.class, i));
		}
		return Response.status(200).entity(mapper.writeValueAsString(events)).build();
	}

	@GET
	@Path("/show_events")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showEvents() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.property("event_id"), "event_id");
		projection.add(Projections.property("name"), "name");
		projection.add(Projections.property("description"), "description");
		projection.add(Projections.property("image"), "image");
		projection.add(Projections.property("status"), "status");
		projection.add(Projections.property("max_score"), "max_score");
		projection.add(Projections.property("location"), "location");
		projection.add(Projections.property("rating"), "rating");

		List<Events> events = (List<Events>) session.createCriteria(Events.class).list();
		return Response.status(200).entity(mapper.writeValueAsString(events)).build();
	}

	@GET
	@Path("/show_sessions/{event_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showSeessions(@PathParam("event_id") int event_id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		Events events = session.get(Events.class, event_id);
		return Response.status(200).entity(mapper.writeValueAsString(events)).build();
	}

	void myEvents() {

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

		Users user = session.get(Users.class, userid);
		if (user.getKSID().contains(ksid))
			return Response.status(200).entity("{\"message\":\"ksid already exist\"}").build();
		user.getKSID().add(ksid);
		t.commit();
		return Response.status(200).entity("{\"message\":\"Added Successfully\"}").build();
	}

	@GET
	@Path("/sendNotificationsAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendNotificationsAll() throws ClientProtocolException, IOException {
		List<Users> users = (List<Users>) session.createCriteria(Users.class).list();
		ArrayList<String> ksids = new ArrayList<String>();
		for (int i = 0; i < users.size(); i++) {
			ksids.addAll(users.get(i).getKSID());
		}
		
		new ScheduledTasks().triggerPush(ksids);
		return Response.status(200).entity("{\"message\":\"sent Successfully\"}").build();
	}
	
	@GET
	@Path("/rating/{eventid}/rate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rating(@PathParam("eventid") Integer eventid, @PathParam("rate") Integer rate)  {
		Events event = session.get(Events.class, eventid);
		event.getRatingList().add(rate);
		t.commit();
		return Response.status(200).entity("{\"message\":\"e	vent rating success \"}").build();
	}
	
	@GET
	@Path("/allusers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allUsers() throws JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
		List<Users> users = (List<Users>) session.createCriteria(Users.class).list();
		return Response.status(200).entity(mapper.writeValueAsString(users)).build();
	}
	
	@GET
	@Path("/userinfo/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userInfo(@PathParam("userid") String userid) throws JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
		Users user = session.get(Users.class, userid);
		return Response.status(200).entity(mapper.writeValueAsString(user)).build();
	}
	
	@GET
	@Path("/likes/{eventid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response likes(@PathParam("eventid") Integer eventid, @PathParam("userid") String userid)  {
		Events event = session.get(Events.class, eventid);
		event.getLikes().add(userid);
		t.commit();
		return Response.status(200).entity("{\"message\":\"like success \"}").build();
	}
	
	@GET
	@Path("/attendance/{userid}/{eventid}/{session_name}/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response attendance(@PathParam("userid") String userid, @PathParam("eventid") Integer eventid, @PathParam("session_name") String session_name, @PathParam("timestamp") String timestamp) throws ParseException {
		
		Events event = session.get(Events.class, eventid);
		
		for(Sessions sess : event.getSessions()){
			if(sess.getSession_name().equals(session_name)){
				if(sess.getUsers_completed_session().contains(userid))
					return Response.status(200).entity("{\"message\":\"Already present\"}").build();
				
				if(true){//sess.timeValidation(timestamp)){
					int sess_score = sess.getScore();
					HashMap<String, Integer> map = event.getUser_scores();
					if(null != map.get(userid)){
					int score = map.get(userid);
					map.put(userid, score+sess_score);
					}
					else
						map.put(userid, sess_score);
				    Users user = session.get(Users.class, userid);
					user.setCumulative_score(user.getCumulative_score()+sess_score);
					
					sess.getUsers_completed_session().add(userid);
				}
				break;
			}
		}
		t.commit();
		return Response.status(200).entity("{\"message\":\"Presence marked successfully\"}").build();
	}
	
	@GET
	@Path("/refreshranks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refreshRanks() {
		//Events rank update
		List<Events> events = (List<Events>) session.createCriteria(Events.class).list();
		
		for(Events event : events ){
			HashMap<String, Integer> scoreMap = event.getUser_scores();
			event.setUsers_rank(scoreMap);
		}
		
		//Users rank update
		List<Users> users = (List<Users>) session.createCriteria(Users.class).list();
		
		HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
		for(Users user : users ){
			scoreMap.put(user.getEmail(), user.getCumulative_score());
		}
		
		Iterator<Entry<String, Integer>> it = scoreMap.entrySet().iterator();
		TreeSet<Integer> sortedscore = new TreeSet<Integer>();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	       sortedscore.add((Integer) pair.getValue());
	    }
	   
	    int rank = 1;
	   
	    HashMap<Integer, Integer> scoreRankMap = new HashMap<Integer, Integer>();
	   for(int i : sortedscore){
		   scoreRankMap.put(i, rank++);
	   }
	
	   HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
		for(String userid : scoreMap.keySet()){
			rankMap.put(userid,scoreRankMap.get(scoreMap.get(userid)));
		}
		
		for(Users user : users ){
			String userId = user.getEmail();
			session.get(Users.class, userId).setRank(rankMap.get(userId));;
		}
		t.commit();
		
		return Response.status(200).entity("{\"message\":\"User and events rank updated\"}").build();
	}
	
}
