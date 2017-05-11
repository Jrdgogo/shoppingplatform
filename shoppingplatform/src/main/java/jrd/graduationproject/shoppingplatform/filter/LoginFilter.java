package jrd.graduationproject.shoppingplatform.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;

public class LoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	public void destroy() {
	}

	private static final String[] IGNORE_URI = { "/css", "/js", "/img", "/images", "/home", "/public", "/favicon.ico","/error"};

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		boolean flag = ignoreUrl(req);

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("User");
		if (flag)
			flag = sessionHave(session);
		
		if (flag)
			flag = cookieHave(req, session);
		

		if (flag) {
			String local = req.getContextPath();
			if (!local.endsWith("/"))
				local = local + "/";
			resp.sendRedirect(local);
			return;
		}
		if (user != null) {
			user = userService.getUserInfo(user.getId());
			session.setAttribute("User", user);
		}
		chain.doFilter(request, response);
	}

	private boolean cookieHave(HttpServletRequest req, HttpSession session) {
		readCookieMap(req);

		Cookie cookie = cookieMap.get("userName");
		if (cookie == null)
			return true;
		String name = cookie.getValue();

		cookie = cookieMap.get("userPwd");
		if (cookie == null)
			return true;
		String value = cookie.getValue();

		User user = new User();
		user.setUsername(name);
		user.setPassword(value);

		user = userService.getUserByName_cookiePwd(user);
		if (user != null && user.getStatus() != null && user.getStatus().getIndex() == 1) {
			session.setAttribute("User", user);
			session.setMaxInactiveInterval(60 * 60 * 24);
			logger.info(user.getUsername() + "通过cookie自动登录了！");
			return false;
		}
		return true;
	}

	private boolean sessionHave(HttpSession session) {

		User user = (User) session.getAttribute("User");
		if (user != null && user.getStatus() != null && user.getStatus().getIndex() == 1)
			return false;

		return true;
	}

	private boolean ignoreUrl(HttpServletRequest req) {
		String url = req.getRequestURL().toString();
		if (url.endsWith(req.getContextPath() + "/"))
			return false;

		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	private IUserService userService;

	public void init(FilterConfig fConfig) throws ServletException {
		userService = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext())
				.getBean(IUserService.class);
	}

	private Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

	private Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

}
