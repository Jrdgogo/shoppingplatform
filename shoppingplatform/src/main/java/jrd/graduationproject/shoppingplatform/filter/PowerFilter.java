package jrd.graduationproject.shoppingplatform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.ModuleEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class PowerFilter implements Filter {

	// private Logger logger = LoggerFactory.getLogger(PowerFilter.class);

	/**
	 * Default constructor.
	 */
	public PowerFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	// private static final Map<String, Integer> POWER_URI = new HashMap<>();
	//
	// static {
	// for (ModuleEnum moduleEnum : ModuleEnum.values()) {
	// POWER_URI.put(moduleEnum.getDesc(), moduleEnum.getIndex());
	// }
	// }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;//获取request对象
		HttpServletResponse resp = (HttpServletResponse) response;//获取response对象

		String url = req.getRequestURL().toString();//获取url
		try {
			ModuleEnum moduleEnum = ModuleEnum.getAdminByUrl(url);//获取该url要访问的模块
			Integer power = moduleEnum.getIndex();//得到该模块的访问权限
			// 非开放区
			if (power > 0) {
				HttpSession session = req.getSession();//获取session对象
				User user = (User) session.getAttribute("User");//获取当前用户对象
				Integer userPower = user.getPower();//获取当前用户具有的角色权限值
				Integer rolePower = AdminEnum.getModulePowerByRolePower(userPower);//获取当前用户具有的模块访问权限值
				if ((power & rolePower) == power) {//符合则访问
					chain.doFilter(request, response);
					return;
				}
			}
		} catch (Exception e) {
		}
		// 无权限
		String local = req.getContextPath();
		if (!local.endsWith("/"))
			local = local + "/";
		resp.sendRedirect(local);//回到主页
		return;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
