package jrd.graduationproject.shoppingplatform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Object user = request.getSession().getAttribute("User");
		if (user != null && modelAndView != null)
			modelAndView.addObject("User", user);
		super.postHandle(request, response, handler, modelAndView);
	}

}