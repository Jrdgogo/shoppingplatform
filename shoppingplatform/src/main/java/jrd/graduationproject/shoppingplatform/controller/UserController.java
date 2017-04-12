package jrd.graduationproject.shoppingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value = { "/user", "/user/baseinfo" })
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/alter.action")
	public String alterUserInfo(User user, Model model) {
		model.addAttribute("User", userService.alterUserInfo(user));
		return "/user/baseinfo";

	}

	@RequestMapping(value = "/baseInfo.action")
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
	public String shopcarAdd(Ware ware, @RequestParam("sessionUserId") String id, Model model) {
		model.addAttribute("shopcar", userService.getaddShopCar( id,ware));
		return shopcarInfo(id, model);

	}
	@RequestMapping(value = "/shopcar/remove.action")
	public String shopcarRemove(ShopCar shopCar,@RequestParam("sessionUserId") String id,  Model model) {
		userService.removeShopCar(shopCar);
		return shopcarInfo(id, model);
		
	}
}
