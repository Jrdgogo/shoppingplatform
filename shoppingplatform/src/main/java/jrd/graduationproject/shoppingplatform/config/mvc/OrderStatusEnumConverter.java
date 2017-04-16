package jrd.graduationproject.shoppingplatform.config.mvc;

import org.springframework.core.convert.converter.Converter;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;

public class OrderStatusEnumConverter implements Converter<String, OrderStatusEnum> {

	@Override
	public OrderStatusEnum convert(String source) {
		return OrderStatusEnum.getStatusByIndex(Integer.valueOf(source));
	}

}
