package jrd.graduationproject.shoppingplatform.config.mvc;

import org.springframework.core.convert.converter.Converter;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;

public class CategoryEnumConverter implements Converter<String, CategoryEnum> {

	@Override
	public CategoryEnum convert(String source) {
		System.out.println(source);
		return CategoryEnum.getCategoryByCode(source);
	}

}
