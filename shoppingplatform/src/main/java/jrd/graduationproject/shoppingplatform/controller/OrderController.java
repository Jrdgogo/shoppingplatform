package jrd.graduationproject.shoppingplatform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping("/user/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IUserService userService;
//	@Autowired
//	private IWareService wareService;

	@RequestMapping(value = "/settlement.action")
	public String Settlement(@RequestParam("id") String[] shopcars, @RequestParam("sessionUserId") String id,
			Model model) {
		model.addAttribute("addrs", userService.getAllAddr(id));
		List<String> shop_cars=new ArrayList<>();
		for(String e:shopcars)
		  shop_cars.add(e);
		List<ShopCar> shopCars = userService.getUserShopCar(shop_cars);
		Double money = 0.0;
		StringBuffer shopcarparam=new StringBuffer();
		for (ShopCar shopCar : shopCars) {

			shopcarparam.append("&shopcarid="+shopCar.getId());
			Ware ware = shopCar.getWare();
			money += ware.getPrice() * shopCar.getWarenum();
		}
		model.addAttribute("ShopCar", shopCars);
		model.addAttribute("money", money);
		model.addAttribute("shopcarparam", shopcarparam.toString().substring(1));
		return "user/order";
	}

	@RequestMapping(value = "/add.ajax")
	public String orderAdd(@RequestParam("sessionUserId") String id, @RequestParam("addrid") String addr,
			@RequestParam("id") List<String> shopcars, Model model) {
		model.addAttribute("order", orderService.createOrder(id, addr, shopcars));
		return "user/order";
	}

}
