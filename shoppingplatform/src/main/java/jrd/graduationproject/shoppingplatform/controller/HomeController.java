package jrd.graduationproject.shoppingplatform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.User;
import jrd.graduationproject.shoppingplatform.service.UserService;

@Controller
@RequestMapping(value="/home")
public class HomeController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/addUser.mvc")
	public String addUser(User user){
		userService.save(user);
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value="/activeUser.mvc")
	public String activeUser(){
		return "激活成功";
	}
}
