package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import java.util.ArrayList;
import java.util.List;

import jrd.graduationproject.shoppingplatform.exception.category.NotFindEnumException;

public enum CategoryEnum {
	
	COMPUTER("电脑","computer",1),
    MOBILEPHONE("手机","mobile phone",2),
	MANSWEAR("男装","man's wear",4),
	WOMANSWEAR("女装","woman's wear",4),
	FOOD("食品","food",8),
	BOOK("图书","book",16);
	
	
	private String name;
	private String code;
	private Integer category;
	private CategoryEnum(String name, String code,Integer category) {
		this.name = name;
		this.code = code;
		this.category=category;
	}
	
	public static CategoryEnum getCategoryByCode(String code) {
		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
			if (categoryEnum.getCode().equals(code))
				return categoryEnum;
		}
		throw new NotFindEnumException("没有发现该枚举类型");
	}
	public static List<CategoryEnum> getCategorysByCategory(Integer category) {
		List<CategoryEnum> categoryEnums=new ArrayList<>();
		
		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
			if (categoryEnum.getCategory()==category)
				categoryEnums.add(categoryEnum);
		}
		
		return categoryEnums;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Integer getCategory() {
		return category;
	}
	
	
	
}
