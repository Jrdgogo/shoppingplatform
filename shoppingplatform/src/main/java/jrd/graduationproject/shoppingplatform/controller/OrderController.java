package jrd.graduationproject.shoppingplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.service.IOrderService;

@Controller
@RequestMapping("/user/order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/get.action")
	public List<Order> getOrdersbyUserId(@RequestParam("sessionUserId")String id){
		
		return orderService.getOrdersbyUserId(id);
		
	}
	

}
