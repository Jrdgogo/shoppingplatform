package jrd.graduationproject.shoppingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value={"/user","/user/baseinfo"})
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/alter.action")
	public String alterUserInfo(User user,Model model){
		model.addAttribute("User",userService.alterUserInfo(user));
		return "/user/baseinfo";
		
	}
	
	@RequestMapping(value = "/userInfo.action")
	public String userInfo(@RequestParam("sessionUserId")String id,Model model){
		model.addAttribute("User",userService.getUserInfo(id));
		return "/user/baseinfo";
		
	}
	
	

}
