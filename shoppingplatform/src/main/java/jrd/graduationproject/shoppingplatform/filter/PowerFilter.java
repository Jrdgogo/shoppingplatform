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

	//private Logger logger = LoggerFactory.getLogger(PowerFilter.class);

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

//	private static final Map<String, Integer> POWER_URI = new HashMap<>();
//
//	static {
//		for (ModuleEnum moduleEnum : ModuleEnum.values()) {
//			POWER_URI.put(moduleEnum.getDesc(), moduleEnum.getIndex());
//		}
//	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String url = req.getRequestURL().toString();
		try {
			ModuleEnum moduleEnum = ModuleEnum.getAdminByUrl(url);
			Integer power = moduleEnum.getIndex();
			// 非开放区
			if (power > 0) {
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute("User");
				Integer userPower = user.getPower();
				Integer rolePower = AdminEnum.getModulePowerByRolePower(userPower);
				if ((power & rolePower) != power) {
					// 无权限
					String local=req.getContextPath();
					if(!local.endsWith("/"))
						local=local+"/";
					resp.sendRedirect(local);
					return;
				}
			}
		} catch (Exception e) {
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
