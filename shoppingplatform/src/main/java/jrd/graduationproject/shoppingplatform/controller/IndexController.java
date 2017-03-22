package jrd.graduationproject.shoppingplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String indexHtml(Model model) {
		return "index";
	}

}
