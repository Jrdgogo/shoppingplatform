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

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Controller
public class IndexController {

	@Autowired
	private IWareService wareService;

	@RequestMapping("/")
	public String indexHtml(Model model, HttpSession session) {

		User user = (User) session.getAttribute("User");
		CategoryEnum[] categoryEnums = fullCategory(model, user);
		Map<String, List<Ware>> wares = new HashMap<>();
		for (CategoryEnum categoryEnum : categoryEnums) {
			PageParam page=new PageParam();
			page.setPagesize(8);
			Ware ware=new Ware();
			ware.setCategory(categoryEnum);
			List<Ware> categoryWares = wareService.getWares(page,ware).getContent();
			if(categoryWares.size()<8)
				continue;
			wares.put(categoryEnum.getName(), categoryWares);
		}
		model.addAttribute("wares", wares);
		
		return "index";
	}

	private CategoryEnum[] fullCategory(Model model, User user) {
		if (user != null) {
			model.addAttribute("User", user);
			model.addAttribute("shopcar", wareService.getUserShopCar(user));
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

	@RequestMapping("/public/{path}.html")
	public String punlicHtml(@PathVariable(value = "path", required = true) String path) {
		return "public/" + path;
	}
			
}
