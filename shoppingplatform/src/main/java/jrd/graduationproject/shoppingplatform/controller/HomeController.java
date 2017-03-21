package jrd.graduationproject.shoppingplatform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value="/home")
public class HomeController {
	
	@Autowired
	private IUserService userService;

}
