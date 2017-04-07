package jrd.graduationproject.shoppingplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Controller
@RequestMapping("/shop")
public class SellerController {
	
	@Autowired
	private IWareService wareService;
	
	@RequestMapping(value="/ware/add.action")
	public List<Order> addWare(){
		
		return null;
		
	}
	

}
