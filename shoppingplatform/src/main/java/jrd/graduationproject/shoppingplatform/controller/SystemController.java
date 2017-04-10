package jrd.graduationproject.shoppingplatform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.ISystemService;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Controller
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private IWareService wareService;
	
	@Autowired
	private ISystemService systemService;

	@RequestMapping(value = "/commodity/show.action")
	public String showCommodity(Model model,HttpSession session) {
		User user = (User) session.getAttribute("User");
		fullCategory(model,user);
		return "system/commodity";
		
	}
	
	private CategoryEnum[] fullCategory(Model model, User user) {
		if (user != null) {
			model.addAttribute("User", user);
		}
		Map<Integer, List<CategoryEnum>> categoryEnumMap = new HashMap<>();
		Map<Integer, List<TypeEnum>> typeEnumMap = new HashMap<>();
		Map<String, List<Commodity>> commodityMap = new HashMap<>();

		CategoryEnum[] categoryEnums = CategoryEnum.values();
		for (CategoryEnum categoryEnum : categoryEnums) {
			// 填充大类
			Integer category = categoryEnum.getCategory();
			List<CategoryEnum> categoryenums = categoryEnumMap.get(category);
			if (categoryenums == null) {
				categoryenums = new ArrayList<>();
				categoryEnumMap.put(category, categoryenums);
			}
			categoryenums.add(categoryEnum);
			// 填充分类
			List<TypeEnum> typeEnums = TypeEnum.getTypesByCategory(categoryEnum);

			List<TypeEnum> typeenums = typeEnumMap.get(category);
			if (typeenums == null) {
				typeenums = new ArrayList<>();
				typeEnumMap.put(category, typeenums);
			}
			typeenums.addAll(typeEnums);

			// 填充小类
			for (TypeEnum typeEnum : typeEnums) {
				String code = typeEnum.getCode();
				List<Commodity> commodities = wareService.getCommoditysByType(typeEnum);
				commodityMap.put(code, commodities);
			}
		}
		model.addAttribute("categorys", categoryEnumMap);
		model.addAttribute("types", typeEnumMap);
		model.addAttribute("commoditys", commodityMap);
		return categoryEnums;
	}
	@RequestMapping(value = "/commodity/add.action")
	public String addCommodity(Model model,Commodity commodity,HttpSession session) {
		User user = (User) session.getAttribute("User");
		fullCategory(model, user);
		model.addAttribute("newCommodity", wareService.addCommodity(commodity));
		return "system/commodity";
		
	}

	@RequestMapping(value = "/admin/add.action")
	public User grantAdmin(@RequestParam("sessionUserId") String id, User user) {
		User admin = new User();
		admin.setId(id);
		return systemService.grantAdmin(admin, user);
	}
	
	@RequestMapping(value = "/admin/show.action")
	public String grantAdmin( User user,PageParam page) {
		List<User> users= systemService.findAllAdmin(user,page);
		
		return "system/admin";
	}

	@RequestMapping(value = "/admin/cancel.action")
	public User cancelAdmin(@RequestParam("sessionUserId") String id, User user) {
		User admin = new User();
		admin.setId(id);
		return systemService.cancelAdmin(admin, user);
	}

	@RequestMapping(value = "/seller/add.action")
	public User grantSeller(User user) {
		return systemService.grantSeller(user);
	}

	@RequestMapping(value = "/seller/cancel.action")
	public User cancelSeller(User user) {
		return systemService.cancelSeller(user);
	}

	@RequestMapping(value = "/user/freeze.action")
	public User freezeUser(User user) {
		return systemService.freezeUser(user);
	}
	@RequestMapping("/{path}.html")
	public String punlicHtml(@PathVariable(value = "path", required = true) String path) {
		return "system/" + path;
	}

}
