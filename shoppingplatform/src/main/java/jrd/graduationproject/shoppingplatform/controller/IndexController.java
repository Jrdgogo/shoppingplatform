package jrd.graduationproject.shoppingplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.User;
import jrd.graduationproject.shoppingplatform.service.UserService;

@Controller
public class IndexController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String indexHtml(Model model){
		List<User> users=userService.getAllUser();
		model.addAttribute("users", users);
		List<Role> roles=userService.getAllRole();
		model.addAttribute("roles", roles);
		return "index";
	}
	
}
