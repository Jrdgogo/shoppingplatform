package jrd.graduationproject.shoppingplatform.config.mvc;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class JacksonHttpMessageConverteConfig {
	
	@Bean(name="mappingJacksonHttpMessageConverter")
	public MappingJackson2HttpMessageConverter JacksonHttpMessageConverter(@Qualifier(value="objectMapper") ObjectMapper objectMapper){
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes=new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.ALL);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converter.setSupportedMediaTypes(supportedMediaTypes);
		converter.setObjectMapper(objectMapper);
		return converter;
	}
	@Bean(name="stringHttpMessageConverter")
	public StringHttpMessageConverter stringHttpMessageConverter(){
		StringHttpMessageConverter converter=new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<MediaType> supportedMediaTypes=new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.ALL);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		converter.setWriteAcceptCharset(false);
		return converter;
	}
	@Bean(name="objectMapper")
	public ObjectMapper objectMapper(){
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper=objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objectMapper=objectMapper.setTimeZone(new SimpleTimeZone(1000*60*60*8, "GMT+8"));
		return objectMapper;
	}

}
