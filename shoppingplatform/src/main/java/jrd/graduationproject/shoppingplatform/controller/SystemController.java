package jrd.graduationproject.shoppingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.ISystemService;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Controller
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private IWareService wareService;
	@Autowired
	private ISystemService systemService;

	@RequestMapping(value = "/addCommodity.action")
	public Commodity addCommodity(Commodity commodity, @RequestParam("commodityCode") String code) {
		commodity.setTypeenum(TypeEnum.getTypeByCode(code));
		return wareService.addCommodity(commodity);
	}

	public User grantAdmin(@RequestParam("sessionUserId")String id, User user){
		User admin=new User();
		return systemService.grantAdmin(admin, user);
	}

//	User cancelAdmin(User admin, User user){
//		
//	}
//
//	User grantSeller(User user);
//
//	User cancelSeller(User user);
//
//	User freezeUser(User user);

}
