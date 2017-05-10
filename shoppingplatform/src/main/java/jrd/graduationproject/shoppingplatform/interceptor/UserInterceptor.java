package jrd.graduationproject.shoppingplatform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;

public class UserInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		IUserService userService = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext())
				.getBean(IUserService.class);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("User");
		if (user != null) {
			user = userService.getUserInfo(user.getId());
			session.setAttribute("User", user);
		}
		if (user != null && modelAndView != null)
			modelAndView.addObject("User", user);
		super.postHandle(request, response, handler, modelAndView);
	}


}