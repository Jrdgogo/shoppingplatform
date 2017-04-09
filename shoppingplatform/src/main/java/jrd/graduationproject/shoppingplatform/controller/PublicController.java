package jrd.graduationproject.shoppingplatform.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.Between;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.pojo.vo.WareQuery;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Controller
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private IWareService wareService;

	@RequestMapping("/ware.action")
	public String wareHtml(@RequestParam(value = "wtype", required = false) String type,
			@RequestParam(value = "id", required = false) String id, Model model, PageParam page, Ware ware,HttpSession session) {

		User user = (User) session.getAttribute("User");
		select(type, id, model, page, ware,user);
		Slice<Ware> slice = wareService.getWares(page, ware);
		model.addAttribute("wares", splitWare(slice.getContent()));
		model.addAttribute("page", slice);
		model.addAttribute("sortprices", sortprice(null));
		model.addAttribute("orderby", "orderby['sales']=DESC&orderby['price']=ASC");
		model.addAttribute("priceAsc", true);
		model.addAttribute("priceorder", "orderby['price']=DESC&orderby['sales']=DESC");
		model.addAttribute("salesAsc", false);
		model.addAttribute("salesorder", "orderby['sales']=ASC&orderby['price']=ASC");

		return "public/phone";
	}

	private List<List<Ware>> splitWare(List<Ware> content) {
		List<List<Ware>> wares = new ArrayList<>();
		int fromIndex = 0;
		int step = 5;
		while (fromIndex < content.size()) {
			int toIndex = fromIndex + step;
			if (toIndex > content.size())
				toIndex = content.size();
			wares.add(content.subList(fromIndex, toIndex));
			fromIndex = toIndex;
		}

		return wares;
	}

	private void select(String type, String id, Model model, PageParam page, Ware ware, User user) {
		if (type == null || id == null) {
			type = "category";
			id = "computer";
		}
		page.setPagesize(25);
		if ("category".equals(type)) {
			CategoryEnum categoryEnum = CategoryEnum.getCategoryByCode(id);
			ware.setCategory(categoryEnum);
			model.addAttribute("keyword", categoryEnum.getName());
		} else if ("type".equals(type)) {
			TypeEnum typeEnum = TypeEnum.getTypeByCode(id);
			ware.setType(typeEnum);
			model.addAttribute("keyword", typeEnum.getName());
		} else if ("commodity".equals(type)) {
			ware.setCommodity(id);
			Commodity commodity = wareService.getCommodityById(id);
			model.addAttribute("keyword", commodity.getName());
		}
		model.addAttribute("wareurl", "/public/ware/select.action?wtype=" + type + "&id=" + id + "&");
		if(user!=null){
			model.addAttribute("User", user);
		    model.addAttribute("shopcar", wareService.getUserShopCar(user));
		}
	}

	@RequestMapping("/ware/select.action")
	public String wareSelectHtml(@RequestParam(value = "wtype") String type, @RequestParam(value = "id") String id,
			WareQuery query, Model model, PageParam page,HttpSession session) {

		User user = (User) session.getAttribute("User");
		select(type, id, model, page, query,user);
		Slice<Ware> slice = wareService.getWares(page, query);
		model.addAttribute("wares", splitWare(slice.getContent()));
		model.addAttribute("page", slice);
		model.addAttribute("sortprices", sortprice(query.getPricebetween()));
		Between between = query.getPricebetween().get("price");
		if (between != null) {
			model.addAttribute("sortprice", between);
			model.addAttribute("sortquery", createQueryParem(between));
		}

		StringBuffer orderby = new StringBuffer("orderby['sales']=");
		String sorder = query.getOrderby().get("sales");
		orderby.append(sorder);

		orderby.append("&orderby['price']=");
		String porder = query.getOrderby().get("price");
		orderby.append(porder);

		model.addAttribute("orderby", orderby.toString());
		model.addAttribute("priceAsc", "ASC".equals(porder) ? true : false);
		model.addAttribute("salesAsc", "ASC".equals(sorder) ? true : false);

		model.addAttribute("priceorder", "orderby['price']=" + conver(porder) + "&orderby['sales']=" + sorder);
		model.addAttribute("salesorder", "orderby['sales']=" + conver(sorder) + "&orderby['price']=" + porder);

		return "public/phone";
	}

	private String conver(String order) {
		if ("DESC".equals(order))
			return "ASC";
		else
			return "DESC";
	}

	private Map<String, Between> sortprice(Map<String, Between> map) {
		Map<String, Between> sortprices = new LinkedHashMap<>();

		Between between = new Between();
		between.setGreaterthen(0);
		between.setLessthen(499);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(500);
		between.setLessthen(999);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(1000);
		between.setLessthen(1699);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(1700);
		between.setLessthen(2699);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(2700);
		between.setLessthen(4499);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(4500);
		between.setLessthen(11999);
		sortprices.put(createQueryParem(between), between);

		between = new Between();
		between.setGreaterthen(12000);
		sortprices.put(createQueryParem(between), between);

		if (map != null && !map.isEmpty())
			for (Between b : map.values()) {
				sortprices.remove(createQueryParem(b));
			}
		return sortprices;
	}

	private String createQueryParem(Between between) {
		if (between == null)
			return null;
		String query = null;
		if (between.getGreaterthen() != null)
			query = "pricebetween['price'].greaterthen=" + between.getGreaterthen();
		if (between.getLessthen() != null)
			query += "&pricebetween['price'].lessthen=" + between.getLessthen();
		return query;
	}

}
