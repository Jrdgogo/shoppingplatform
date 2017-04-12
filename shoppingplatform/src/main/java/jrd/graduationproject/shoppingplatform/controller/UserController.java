package jrd.graduationproject.shoppingplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value = { "/user"})
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/baseInfo/alter.action")
	public String alterUserInfo(User user, Model model) {
		model.addAttribute("User", userService.alterUserInfo(user));
		return "/user/baseinfo";

	}

	@RequestMapping(value = "/baseInfo/show.action")
	public String userInfo(@RequestParam("sessionUserId") String id, Model model) {
		model.addAttribute("User", userService.getUserInfo(id));
		return "/user/baseinfo";

	}

	@RequestMapping(value = "/shopcar/Info.action")
	public String shopcarInfo(@RequestParam("sessionUserId") String id, Model model) {
		model.addAttribute("shopcars", userService.getUserShopCar(id));
		return "/user/shopcarInfo";

	}

	@RequestMapping(value = "/shopcar/add.action")
	public String shopcarAdd(Ware ware, @RequestParam("sessionUserId") String id, Model model,
			@RequestParam("num") Integer num) {
		model.addAttribute("shopcar", userService.getaddShopCar( id,ware,num));
		return shopcarInfo(id, model);

	}
	@RequestMapping(value = "/shopcar/remove.action")
	public String shopcarRemove(@RequestParam("id") List<String> shopcars,@RequestParam("sessionUserId") String id,  Model model) {
		userService.removeShopCar(shopcars);
		return shopcarInfo(id, model);
		
	}
	@ResponseBody
	@RequestMapping(value = "/address/add.ajax")
	public UserWareAddr addWareAdde(UserWareAddr addr,@RequestParam("sessionUserId") String id) {
		return userService.addAddr(id,addr);
	}
	@ResponseBody
	@RequestMapping(value = "/address/alter.ajax")
	public UserWareAddr alterWareAddr(UserWareAddr addr) {
		return userService.alterAddr(addr);
	}
}
