package com.kony;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value = "/addLogin", method = RequestMethod.POST)
	   public String addStudent(@ModelAttribute("SpringWeb")Login login, 
	   ModelMap model) {
	      model.addAttribute("userid", login.getUsername());
	      model.addAttribute("pswd", login.getPassword());
	      
	      System.out.println("Username is "+login.getUsername());
	      
	      return "result";
	   }
}