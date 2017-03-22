package jrd.graduationproject.shoppingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

	@Autowired
	private IUserService userService;

}
