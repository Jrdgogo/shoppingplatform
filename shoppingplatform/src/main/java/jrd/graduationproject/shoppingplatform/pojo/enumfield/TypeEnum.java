package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import java.util.ArrayList;
import java.util.List;

import jrd.graduationproject.shoppingplatform.exception.category.NotFindEnumException;

public enum TypeEnum {
	
	COMPUTER("电脑整机","computer",CategoryEnum.COMPUTER),
	COMPUTERACCESSORIES("电脑配件","computer accessories",CategoryEnum.COMPUTER),
    MOBILEPHONE("手机","mobile phone",CategoryEnum.MOBILEPHONE),
    MOBILEACCESSORIES("手机配件","mobile accessories",CategoryEnum.MOBILEPHONE),
	MANSWEAR("男装","man's wear",CategoryEnum.MANSWEAR),
	MANSSHOES("男鞋","men's shoes",CategoryEnum.MANSWEAR),
	WOMANSWEAR("女装","woman's wear",CategoryEnum.WOMANSWEAR),
	WOMANSSHOES("女鞋","woman's shoes",CategoryEnum.WOMANSWEAR),
	FRUITS("新鮮水果","fruits",CategoryEnum.FOOD),
	SNACKS("休闲食品","snacks",CategoryEnum.FOOD),
	BEVERAGE("饮料冲调","beverage",CategoryEnum.FOOD),
	EDUCATION("教育","education",CategoryEnum.BOOK),
	SCIENCE("科技","science",CategoryEnum.BOOK),
	LITERATURE("文艺","literature",CategoryEnum.BOOK);
	
	private String name;
	private CategoryEnum categoryEnum;
	private String code;
	private TypeEnum(String name, String code,CategoryEnum categoryEnum) {
		this.name = name;
		this.code = code;
		this.categoryEnum=categoryEnum;
	}
	
	public static TypeEnum getTypeByCode(String code) {
		for (TypeEnum typeEnum : TypeEnum.values()) {
			if (typeEnum.getCode().equals(code))
				return typeEnum;
		}
		throw new NotFindEnumException("没有发现该枚举类型");
	}
	
	public static List<TypeEnum> getTypesByCategory(CategoryEnum categoryEnum) {
		List<TypeEnum> typeEnums=new ArrayList<>();
		
		for (TypeEnum typeEnum : TypeEnum.values()) {
			if (typeEnum.getCategoryEnum().equals(categoryEnum))
				 typeEnums.add(typeEnum);
		}
		return typeEnums;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public CategoryEnum getCategoryEnum() {
		return categoryEnum;
	}

}
