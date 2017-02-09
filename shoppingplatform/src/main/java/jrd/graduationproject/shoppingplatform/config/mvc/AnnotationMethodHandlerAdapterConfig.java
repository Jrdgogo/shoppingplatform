package jrd.graduationproject.shoppingplatform.config.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

//@Configuration
public class AnnotationMethodHandlerAdapterConfig {

	@Bean(name="requestMappingHandlerAdapter")
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter(
			@Qualifier(value="mappingJacksonHttpMessageConverter") MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
			@Qualifier(value="stringHttpMessageConverter") StringHttpMessageConverter stringHttpMessageConverter){
		RequestMappingHandlerAdapter handlerAdapter=new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> converters=handlerAdapter.getMessageConverters();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(5);
		for(HttpMessageConverter<?> converter:converters){
			if(converter instanceof StringHttpMessageConverter){
				messageConverters.add(stringHttpMessageConverter);
			}else
			   messageConverters.add(converter);
		}
		messageConverters.add(mappingJackson2HttpMessageConverter);
		handlerAdapter.setMessageConverters(messageConverters);
		return handlerAdapter;
	}
}
