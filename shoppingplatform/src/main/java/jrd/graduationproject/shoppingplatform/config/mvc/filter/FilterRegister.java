package jrd.graduationproject.shoppingplatform.config.mvc.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.filter.LoginFilter;
import jrd.graduationproject.shoppingplatform.filter.ParamFilter;
import jrd.graduationproject.shoppingplatform.filter.PowerFilter;
import jrd.graduationproject.shoppingplatform.filter.ReturnJSonFilter;

@Component
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
		registration.addUrlPatterns("/*");
		registration.setName("powerFilter");
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public FilterRegistrationBean paramFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new ParamFilter());
		registration.addUrlPatterns("*.action");
		registration.setName("paramFilter");
		registration.setOrder(3);
		return registration;
	}

	@Bean
	public FilterRegistrationBean returnJSonFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new ReturnJSonFilter());
		registration.addUrlPatterns("*.action");
		registration.setName("returnJSonFilter");
		registration.setOrder(4);
		return registration;
	}
}
