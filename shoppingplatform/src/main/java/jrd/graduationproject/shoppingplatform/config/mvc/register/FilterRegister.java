package jrd.graduationproject.shoppingplatform.config.mvc.register;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jrd.graduationproject.shoppingplatform.filter.LoginFilter;
import jrd.graduationproject.shoppingplatform.filter.ParamFilter;
import jrd.graduationproject.shoppingplatform.filter.PowerFilter;
import jrd.graduationproject.shoppingplatform.filter.ReturnJSonFilter;

@Configuration
public class FilterRegister {

	@Bean
	public FilterRegistrationBean loginFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new LoginFilter());
		registration.addUrlPatterns("/*");
		registration.setName("loginFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean powerFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new PowerFilter());
		registration.addUrlPatterns("/system/*","/user/*","/shop/*");
		registration.setName("powerFilter");
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public FilterRegistrationBean paramFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new ParamFilter());
		registration.addUrlPatterns("*.action","*.html","*.ajax");
		registration.setName("paramFilter");
		registration.setOrder(3);
		return registration;
	}

	@Bean
	public FilterRegistrationBean returnJSonFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new ReturnJSonFilter());
		registration.addUrlPatterns("*.ajax");
		registration.setName("returnJSonFilter");
		registration.setOrder(4);
		return registration;
	}
	
}
