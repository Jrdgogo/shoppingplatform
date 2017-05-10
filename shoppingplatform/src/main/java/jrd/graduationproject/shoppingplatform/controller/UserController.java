package jrd.graduationproject.shoppingplatform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.SexEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.service.ISystemService;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Controller
@RequestMapping(value = { "/user" })
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ISystemService systemService;
	@Autowired
	private OrderController orderController;
	@Autowired
	private IOrderService orderService;

	@RequestMapping(value = "/baseInfo/alter.action")
	public String alterUserInfo(User user,Model model,@RequestParam("sessionUserId") String id) {
		user.setId(id);
		userService.alterUserInfo(user);
		return userinfoHtml(model,id,new PageParam());

	}
	@ResponseBody
	@RequestMapping(value = "/updatePW.ajax")
	public String updatePW(@RequestParam("sessionUserId") String id,
			@RequestParam("oldpw") String oldpw,
			@RequestParam("newpw") String newpw,
			@RequestParam("type") String type) {
		
		return userService.updatePW(oldpw,newpw,type,id);

	}


	@RequestMapping(value = "/shopcar/Info.action")
	public String shopcarInfo(@RequestParam("sessionUserId") String id, Model model) {
		model.addAttribute("shopcars", userService.getUserShopCar(id));
		model.addAttribute("User", userService.getUserInfo(id));
		return "/user/shopcarInfo";

	}

	@RequestMapping(value = "/shopcar/add.action")
	public String shopcarAdd(Ware ware, @RequestParam("sessionUserId") String id, Model model,
			@RequestParam("num") Integer num) {
		model.addAttribute("shopcar", userService.getaddShopCar(id, ware, num));
		model.addAttribute("checked", "checked");
		return shopcarInfo(id, model);

	}

	@RequestMapping(value = "/shopcar/remove.action")
	public String shopcarRemove(@RequestParam("id") String[] shopcars, @RequestParam("sessionUserId") String id,
			Model model) {
		List<String> array = new ArrayList<>();
		for (String shopcar : shopcars) {
			array.add(shopcar);
		}
		userService.removeShopCar(array);
		return shopcarInfo(id, model);

	}

	
	@RequestMapping(value = "/address/add.action")
	public String addWareAdde(UserWareAddr addr, @RequestParam("sessionUserId") String id, Model model,
			@RequestParam("shopcarid") String ids) {
		model.addAttribute("caddr", userService.addAddr(id, addr));
		return orderController.Settlement(ids.split("&shopcarid="), id, model);
	}

	
	@RequestMapping(value = "/address/alter.action")
	public String alterWareAddr(UserWareAddr addr, Model model, @RequestParam("sessionUserId") String id,
			@RequestParam("shopcarid") String ids) {
		model.addAttribute("caddr", userService.alterAddr(addr));

		return orderController.Settlement(ids.split("&shopcarid="), id, model);
	}

	@ResponseBody
	@RequestMapping("/apply.controller")
	public Boolean apply(@PathParam("type") Integer type, HttpSession session,
			@RequestParam(value = "sname", required = false) String sname, MultipartFile file) throws IOException {
		User user = (User) session.getAttribute("User");
		String id = user.getId();
		if (type == 1) {
			Seller seller = new Seller();
			seller.setName(sname);
			seller.setLogo(GlobalUtil.savePhoto(file));
			userService.applySeller(seller, id);
		}
		return systemService.apply(type, id);
	}

	@RequestMapping("/userinfo.html")
	public String userinfoHtml(Model model,@RequestParam("sessionUserId") String id,PageParam page) {
		
		baseOrder(model);
		Slice<Order> pages=orderService.getOrdersbyUserId(id,page);
		model.addAttribute("orders",pages.getContent() );
		model.addAttribute("page",pages);
		model.addAttribute("type", "0");
		model.addAttribute("querydate", "3");
		model.addAttribute("sd", "近三个月订单");
		model.addAttribute("ss", "全部状态");
		return "user/userinfo" ;
	}
	private void baseOrder(Model model) {
		model.addAttribute("UNPAID", orderService.queryCountByStatus(OrderStatusEnum.UNPAID));
		model.addAttribute("PAYMENT", orderService.queryCountByType(1));
		model.addAttribute("COMMENT", orderService.queryCountByType(2));
		model.addAttribute("MAN", SexEnum.MAN);
		model.addAttribute("WOMEN", SexEnum.WOMEN);
	}
	
	@RequestMapping("/queryorder.action")
	public String queryorder(Model model,@RequestParam("sessionUserId") String id,PageParam page,
			@RequestParam("type") String type,
			@RequestParam(value="querydate",defaultValue="3") Long querydate) {
		baseOrder(model);
		Date date=new Date();
		Long time=date.getTime()-querydate*1000*60*60*24*30;
		date=new Date(time);
		model.addAttribute("type", type);
		model.addAttribute("querydate", querydate);
		Slice<Order> pages=orderService.getOrdersbyUserId(id,page,type,date);
		model.addAttribute("orders",pages.getContent() );
		model.addAttribute("page",pages);
		if(querydate==3)
		   model.addAttribute("sd", "近三个月订单");
		else if(querydate==6)
		   model.addAttribute("sd", "近半年订单");
		else if(querydate==12)
			   model.addAttribute("sd", "近一年订单");
		else if(querydate==36)
			   model.addAttribute("sd", "近三年订单");
		if("0".equals(type))
		    model.addAttribute("ss", "全部状态");
		else if("1".equals(type))
		    model.addAttribute("ss", "等待付款");
		else if("2".equals(type))
		    model.addAttribute("ss", "等待收货");
		else if("4".equals(type))
		    model.addAttribute("ss", "已完成");
		else if("5".equals(type))
		    model.addAttribute("ss", "已取消");
		return "user/userinfo" ;
	}
	@RequestMapping("/{path}.html")
	public String userHtml(@PathVariable(value = "path", required = true) String path) {

		return "user/" + path;
	}
	
	@RequestMapping(value = "/querybyid.action")
	public String option(@RequestParam("orderid") String orderId,Model model,@RequestParam("sessionUserId") String id) {
		List<Order> orders=new ArrayList<>();
		orders.add( orderService.queryById(orderId));
		model.addAttribute("orders",orders);
		baseOrder(model);
		model.addAttribute("page",new PageImpl<>(orders,new PageRequest(0, 10),(long)1));
		model.addAttribute("type", "0");
		model.addAttribute("querydate", "3");
		model.addAttribute("sd", "近三个月订单");
		model.addAttribute("ss", "全部状态");
		return "user/userinfo";
	}
}
