package jrd.graduationproject.shoppingplatform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
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
	public String showCommodity(Model model, HttpSession session) {
		User user = (User) session.getAttribute("User");
		fullCategory(model, user);
		return "system/commodity";

	}

	@ResponseBody
	@RequestMapping(value = "/commodity/type.action")
	public List<TypeEnum> showtype(CategoryEnum categoryEnum) {
		return TypeEnum.getTypesByCategory(categoryEnum);

	}

	private CategoryEnum[] fullCategory(Model model, User user) {
		if (user != null) {
			model.addAttribute("User", user);
		}
		Map<Integer, List<CategoryEnum>> categoryEnumMap = new HashMap<>();
		Map<Integer, List<TypeEnum>> typeEnumMap = new HashMap<>();
		Map<String, List<Commodity>> commodityMap = new HashMap<>();

		CategoryEnum[] categoryEnums = CategoryEnum.values();
		model.addAttribute("selectcategory", categoryEnums);
		model.addAttribute("selecttype", TypeEnum.getTypesByCategory(categoryEnums[0]));
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
	public String addCommodity(Model model, Commodity commodity, HttpSession session) {
		model.addAttribute("newCommodity", wareService.addCommodity(commodity));
		return showCommodity(model, session);

	}

	@RequestMapping(value = "/admin/add.action")
	public String grantAdmin(Model model, @RequestParam("sessionUserId") String id,
			User user,@RequestParam("message") String msgid) {
		User admin = new User();
		admin.setId(id);
		systemService.grantAdmin(admin, user);
		systemService.handleMessage(msgid);
		return message(model, new PageParam(),id);
	}

	@RequestMapping(value = "/message/show.action")
	public String message(Model model, PageParam page,@RequestParam("sessionUserId") String id) {
		Page<Message> messages = systemService.selectMessage(page,id);
		model.addAttribute("message", messages.getContent());
		model.addAttribute("page", messages);
		return "system/message";
	}

	@RequestMapping(value = "/admin/show.action")
	public String showAdmin(Model model, PageParam page) {
		Page<User> p = systemService.SelectUserByType(AdminEnum.ADMIN, page);
		model.addAttribute("users", p.getContent());
		model.addAttribute("page", p);
		return "system/admin";
	}

	@RequestMapping(value = "/admin/cancel.action")
	public String cancelAdmin(Model model, @RequestParam("sessionUserId") String id, User user) {
		User admin = new User();
		admin.setId(id);
		systemService.cancelAdmin(admin, user);
		return showAdmin(model, new PageParam());
	}

	@RequestMapping(value = "/seller/add.action")
	public String grantSeller(Model model, User user,
			@RequestParam("message") String msgid,
			@RequestParam("sessionUserId") String id) {
		systemService.grantSeller(user);
		systemService.handleMessage(msgid);
		return message(model, new PageParam(),id);
	}

	@RequestMapping(value = "/seller/show.action")
	public String showSeller(Model model, PageParam page) {
		Page<User> p = systemService.SelectUserByType(AdminEnum.SHOPKEEPER, page);
		model.addAttribute("users", p.getContent());
		model.addAttribute("page", p);
		return "system/seller";
	}

	@RequestMapping(value = "/seller/cancel.action")
	public String cancelSeller(Model model, User user) {
		systemService.cancelSeller(user);
		return showSeller(model, new PageParam());
	}

	@RequestMapping(value = "/user/show.action")
	public String showUser(Model model, PageParam page) {
		Page<User> p = systemService.SelectUserByType(AdminEnum.USER, page);
		model.addAttribute("users", p.getContent());
		model.addAttribute("page", p);
		return "system/user";
	}
	
	@RequestMapping(value = "/user/freeze.action")
	public String freezeUser(Model model,User user) {
		systemService.freezeUser(user);
		return showUser(model, new PageParam());
	}
	
	@RequestMapping(value = "/user/active.action")
	public String activeUser(Model model,User user) {
		systemService.activeUser(user);
		return showUser(model, new PageParam());
	}

	@RequestMapping("/{path}.html")
	public String punlicHtml(@PathVariable(value = "path", required = true) String path) {
		return "system/" + path;
	}

}
