package jrd.graduationproject.shoppingplatform.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.pojo.vo.WareQuery;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.service.IWareService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Controller
@RequestMapping("/shop")
public class SellerController {

	@Autowired
	private IWareService wareService;
	private IOrderService orderService;

	@ResponseBody
	@RequestMapping(value = "/ware/add.controller", method = RequestMethod.POST)
	public Boolean addWare(Model model, Ware ware, HttpSession session, MultipartFile file)
			throws IOException {
		User user=(User) session.getAttribute("User");
		String id=user.getId();
		ware.setSeller(id);
		ware.setPhoto(GlobalUtil.savePhoto(file));
		return wareService.addWare(ware);
	}

	@RequestMapping(value = "/ware/alter.controller", method = RequestMethod.POST)
	public String alterWare(Model model, Ware ware, HttpSession session, MultipartFile file)
			throws IOException {
		User user=(User) session.getAttribute("User");
		String id=user.getId();
		ware.setSeller(id);
		if (!file.isEmpty())
			ware.setPhoto(GlobalUtil.savePhoto(file));
		wareService.alterWare(ware);
		return showWare(id, new PageParam(), new WareQuery(), model);
	}

	@RequestMapping(value = "/ware/show.action")
	public String showWare(@RequestParam("sessionUserId") String id, PageParam page, WareQuery query, Model model) {
		query.getOrderby().put("update", "DESC");
		query.setSeller(id);
		Slice<Ware> wares = wareService.getWares(page, query);
		model.addAttribute("wares", wares.getContent());
		model.addAttribute("page", wares);
		return "seller/ware";
	}
	
	@RequestMapping(value = "/order/show.action")
	public String showWare(@RequestParam("sessionUserId") String id,Order order,PageParam page,Model model) {
	
		Slice<Order> orders = orderService.getOrdersBySeller(order,id,page);
		model.addAttribute("orders", orders.getContent());
		model.addAttribute("page", orders);
		return "seller/order";
	}

}
