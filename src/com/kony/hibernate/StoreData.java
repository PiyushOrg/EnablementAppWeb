package com.kony.hibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;  
  
public class StoreData {  
public static void main(String[] args) {  
      
    //creating configuration object  
    Configuration cfg=new Configuration();  
    cfg.configure();//populates the data of the configuration file  
      
    //creating seession factory object  
    SessionFactory factory=cfg.buildSessionFactory();  
    
    //creating session object  
    Session session=factory.openSession();  
      
    //creating transaction object  
    Transaction t=session.beginTransaction();  
    ArrayList<String> likes = new ArrayList<String>();
    Date st = new Date(1212312312);
    Date et = new Date(1212312342);
    
    Collection<Sessions> allses= new ArrayList<Sessions>();
    ArrayList<String> users_completed_session = new ArrayList<String>();
    
    //Event 1
    Events e1=new Events(); 
     e1.setName("Kony Visualizer 8.0 handson");
     e1.setDescription("Hands on all new features added.");
     e1.setLocation("Phoenix");
     

     likes.add("piyush.mittal@kony.com");
     likes.add("karan@kony.com");
     e1.setLikes(likes);
     
     e1.setMax_score(50);
     e1.getRatingList().add(3);
     
     //Session 1 of event 1
     Sessions sess_e1_s1 = new Sessions();
     
     sess_e1_s1.setSession_name("Kony Vizualiser first session");
     sess_e1_s1.setSesion_description("Demo of installation steps");

     sess_e1_s1.setSession_st(st);
     sess_e1_s1.setSession_et(et);
     
     users_completed_session.add("piyush.mittal@kony.com");
     users_completed_session.add("karan@kony.com");
     
     //Session 2 of event 1
     Sessions sess_e1_s2 = new Sessions();
     
     sess_e1_s2.setSession_name("Kony Vizualiser second session");
     sess_e1_s2.setSesion_description("Demo of ffi steps");
     st = new Date(1212312312);
     et = new Date(1212312342);
     sess_e1_s2.setSession_st(st);
     sess_e1_s2.setSession_et(et);
     users_completed_session = sess_e1_s1.getUsers_completed_session();
     users_completed_session = sess_e1_s2.getUsers_completed_session();
     users_completed_session.add("piyush.mittal@kony.com");
     users_completed_session.add("karan@kony.com");
     
       
    

    allses.add(sess_e1_s1);
    allses.add(sess_e1_s2);
    
    e1.setSessions(allses);
    
  //Event 2
     Events e2=new Events(); 
     allses= new ArrayList<Sessions>();
     e2.setName("Kony Mobile fabric handson");
     e2.setDescription("Hands on all new features added on Mobile fabric.");
     e2.setLocation("Ganga");
     
     likes = new ArrayList<String>();
     likes.add("mukul@kony.com");
     likes.add("karan@kony.com");
     e2.setLikes(likes);
     e2.getRatingList().add(4);
     
     e2.setMax_score(100);
     
     //Session 1 of event 2
     Sessions sess_e2_s1 = new Sessions();
     
     sess_e2_s1.setSession_name("Kony Mobile fabric first session");
     sess_e2_s1.setSesion_description("Demo of installation steps");
     st = new Date(1212312312);
     et = new Date(1212312342);
     sess_e2_s1.setSession_st(st);
     sess_e2_s1.setSession_et(et);
     
     users_completed_session = sess_e2_s1.getUsers_completed_session();
     users_completed_session.add("mukul@kony.com");
     users_completed_session.add("karan@kony.com");
     
     //Session 2 of event 2
     Sessions sess_e2_s2 = new Sessions();
     
     sess_e2_s2.setSession_name("Kony Mobile Fabric second session");
     sess_e2_s2.setSesion_description("Demo of integration steps");
     st = new Date(1212312312);
     et = new Date(1212312342);
     sess_e2_s2.setSession_st(st);
     sess_e2_s2.setSession_et(et);
     
     users_completed_session = sess_e2_s2.getUsers_completed_session();
     users_completed_session.add("piyush.mittal@kony.com");
     users_completed_session.add("karan@kony.com");
     
     allses.add(sess_e2_s1);
     allses.add(sess_e2_s2);
     
     e2.setSessions(allses);
     
     
     
   //Event 3
     Events e3=new Events(); 
     e3.setName("Visualizer MVC Workshop");
     e3.setDescription("Hands on all new features added on Kony MVC.");
     e3.setLocation("Ganga");
     
     likes = new ArrayList<String>();
     likes.add("mukul@kony.com");
     likes.add("karan@kony.com");
     e3.setLikes(likes);
     e3.getRatingList().add(4);
     
     e3.setMax_score(100);
     
     //Session 1 of event 3
     Sessions sess_e3_s1 = new Sessions();
     
     sess_e3_s1.setSession_name("Kony MVC first session");
     sess_e3_s1.setSesion_description("Demo of installation steps");
     st = new Date(1212312312);
     et = new Date(1212312342);
     sess_e3_s1.setSession_st(st);
     sess_e3_s1.setSession_et(et);
     
     users_completed_session = sess_e3_s1.getUsers_completed_session();
     users_completed_session.add("mukul@kony.com");
     users_completed_session.add("karan@kony.com");
     
     //Session 2 of event 3
     Sessions sess_e3_s2 = new Sessions();
     
     sess_e3_s2.setSession_name("Kony MVC session");
     sess_e3_s2.setSesion_description("Demo of integration steps");
     st = new Date(1212312312);
     et = new Date(1212312342);
     sess_e3_s2.setSession_st(st);
     sess_e3_s2.setSession_et(et);
     
     users_completed_session = sess_e3_s2.getUsers_completed_session();
     users_completed_session.add("piyush.mittal@kony.com");
     users_completed_session.add("karan@kony.com");
     
    
    allses= new ArrayList<Sessions>();
    allses.add(sess_e3_s1);
    allses.add(sess_e3_s2);
    
    e3.setSessions(allses);
    
    session.save(e1);//persisting the object 
    session.save(e2);//persisting the object
    session.save(e3);//persisting the object
    
    
    //Add User 
      
    Users usr = new Users();
    usr.setEmail("m@piyushmittal.com");
    usr.setName("Piyush Mittal");
    usr.getKSID().add("asdfasdfads");
    session.save(usr);
    t.commit();//transaction is committed  
    session.close();  
      
    System.out.println("successfully saved");  
      
}  
}  