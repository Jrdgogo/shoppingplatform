package jrd.graduationproject.shoppingplatform.config.mvc;

import org.springframework.core.convert.converter.Converter;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;

public class TypeEnumConverter implements Converter<String, TypeEnum> {

	@Override
	public TypeEnum convert(String source) {
		return TypeEnum.getTypeByCode(source);
	}

}
