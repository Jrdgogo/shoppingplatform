package jrd.graduationproject.shoppingplatform.config.mvc.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import jrd.graduationproject.shoppingplatform.config.mvc.CategoryEnumConverter;
import jrd.graduationproject.shoppingplatform.config.mvc.TypeEnumConverter;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;

@Configuration
public class FormatterRegister {

	@Bean
	public Converter<String, CategoryEnum> formatterCategoryEnumRegistration() {
		return new CategoryEnumConverter();
	}
	@Bean
	public Converter<String, TypeEnum> formatterTypeEnumRegistration() {
		return new TypeEnumConverter();
	}
}
