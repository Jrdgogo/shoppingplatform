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
		if (user != null)
			model.addAttribute("User", user);
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
		PageParam page = new PageParam();
		page.setPagenum(1);
		page.setPagesize(6);
		Page<Commodity> pageModel = wareService.getCommoditys(page);
		List<Commodity> commodities = pageModel.getContent();
		page.setPagenum(1);
		page.setPagesize(8);
		for (Commodity commodity : commodities) {
			Page<Ware> pageWare = wareService.getWares(commodity, page);
			model.addAttribute(commodity.getId(), pageWare.getContent());
		}

		return "index";
	}

}
