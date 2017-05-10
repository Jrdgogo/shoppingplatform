package jrd.graduationproject.shoppingplatform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;
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
		model.addAttribute("shopcars", shopCars);
		model.addAttribute("money", money);
		if(shopcarparam.length()>"&shopcarid=".length())
		   model.addAttribute("shopcarparam", shopcarparam.toString().substring("&shopcarid=".length()));
		else
		   model.addAttribute("shopcarparam", shopcarparam.toString());
		return "user/order";
	}

	@ResponseBody
	@RequestMapping(value = "/add.ajax")
	public Order orderAdd(@RequestParam("sessionUserId") String id, @RequestParam("addrid") String addr,
			@RequestParam("shopcarid") List<String> shopcars) {
		Order order= orderService.createOrder(id, addr, shopcars);
		return order;
	}
	
	@ResponseBody
	@RequestMapping(value = "/defrayOrder.ajax")
	public Order defrayOrder(@RequestParam("orderid") String orderId,
			@RequestParam("pwd") String pwd,
			@RequestParam("sessionUserId") String id) {
		Order order= orderService.defrayOrder(orderId,id,pwd);
		return order;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancalOrder.ajax")
	public Order cancalOrder(@RequestParam("orderid") String orderId) {
		return  orderService.cancalOrder(orderId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/backOrder.ajax")
	public Order backOrder(@RequestParam("orderid") String orderId) {
		return  orderService.backOrder(orderId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/option.ajax")
	public Order option(@RequestParam("orderid") String orderId,
			@RequestParam("type") Integer type) {
		Order order=null;
		 if(type==0)
			 order=orderService.cancalOrder(orderId);
		 else if(type==1)
			 order=orderService.defrayOrder(orderId);
		 else if(type==3)
			 order=orderService.backOrder(orderId);
		 return order;
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment.ajax")
	public String option(@RequestParam("orderid") String orderId,
			@RequestParam("sessionUserId") String id,
			@RequestParam("txt") String txt) {
		return orderService.addComment(orderId,id,txt);
	}

}
