package jrd.graduationproject.shoppingplatform.config.mvc.register;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jrd.graduationproject.shoppingplatform.interceptor.ActionInterceptor;

@Configuration
public class InterceptorRegister extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ActionInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
		super.addInterceptors(registry);
	}

}
