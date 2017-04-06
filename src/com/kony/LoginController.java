package com.kony;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kony.hibernate.Login;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		
		return new ModelAndView("login", "coommand", new Login());
	}
	
	@RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
	   public String addStudent(@ModelAttribute("SpringWeb")Login login, 
	   ModelMap model) {
	     
	      System.out.println("Username is "+login.getUserid());
	      if (login.getUserid().equals("admin@kony.com") && login.getPswd().equals("Kony@2017"))
	      return "createevent";
	      else 
	    	  return "login";
	   }
}