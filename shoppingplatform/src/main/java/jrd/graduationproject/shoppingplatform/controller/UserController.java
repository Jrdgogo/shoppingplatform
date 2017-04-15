package jrd.graduationproject.shoppingplatform.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.service.ISystemService;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Controller
@RequestMapping(value = { "/user"})
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ISystemService systemService;
	

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
	@ResponseBody
	@RequestMapping("/apply.controller")
	public Boolean apply(@PathParam("type")Integer type,HttpSession session,
			@RequestParam(value="sname",required=false) String sname, MultipartFile file) throws IOException {
		User user=(User) session.getAttribute("User");
		String id=user.getId();
		if(type==1){
			Seller seller=new Seller();
			seller.setName(sname);
			seller.setLogo(GlobalUtil.savePhoto(file));
			userService.applySeller(seller,id);
		}
		return systemService.apply(type,id);
	}

	
	@RequestMapping("/{path}.html")
	public String userHtml(@PathVariable(value = "path", required = true) String path) {
		
		return "user/" + path;
	}
}
