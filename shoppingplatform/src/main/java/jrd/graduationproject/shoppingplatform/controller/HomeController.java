package jrd.graduationproject.shoppingplatform.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/logout.action")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();

		Cookie cookieName = new Cookie("userName", "");
		cookieName.setMaxAge(0);
		cookieName.setPath("/");
		response.addCookie(cookieName);

		Cookie cookiePwd = new Cookie("userPwd", "");
		cookiePwd.setMaxAge(0);
		cookiePwd.setPath("/");
		response.addCookie(cookiePwd);

		return "redirect:/";
	}

	
	@ResponseBody
	@RequestMapping(value = "/getUserByName.action")
	public User getUserByName(@RequestParam("username") String username) {
		return userService.getUserByName(username);
	}

	@ResponseBody
	@RequestMapping(value = "/activationUser.action", produces = { "text/html;charset=UTF-8" })
	public String ActivationUser(@RequestParam("id") String id, @RequestParam("activeCode") String activeCode) {
		User user = userService.ActivationUser(id, activeCode);
		if (user != null)
			return "尊敬的用户:" + user.getUsername() + ",您已经成功激活！请进入官网进行登录！";
		return "尊敬的用户,抱歉通知您！该用户激活失败！请重新点击链接！";
	}

	@ResponseBody
	@RequestMapping(value = "/registerUser.action")
	public Integer RegisterUser(HttpSession session, @RequestParam("code") String code, @RequestParam("pwd") String pwd,
			User user) {
		String imgcode = (String) session.getAttribute("imgcode");
		session.removeAttribute("imgcode");
		// 验证码错误
		if (imgcode == null || !imgcode.equals(code))
			return 2;
		// 两次密码不一致
		if (!pwd.equals(user.getPassword()))
			return 3;
		// 注册成功
		if (userService.RegisterUser(user) != null)
			return 1;
		return 0;
	}

	private static final int SESSION_OUTTIME = 60 * 60 * 24;

	@ResponseBody
	@RequestMapping(value = "/loginValidate.action")
	public Integer loginValidate(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			User user) {
		User u = userService.getUserByName_Pwd(user);

		// 用戶不存在
		if (u == null)
			return -1;
		// 用户未激活或已註銷
		if (!(u.getStatus().equals(StatusEnum.ACTIVE)))
			return u.getStatus().getIndex();

		// 用戶登錄成功，設立cookie和session
		String userName = u.getUsername();
		String userPwd = u.getPassword();

		Cookie cookieName = new Cookie("userName", userName);
		cookieName.setMaxAge(SESSION_OUTTIME);
		cookieName.setPath("/");
		response.addCookie(cookieName);

		Cookie cookiePwd = new Cookie("userPwd", userPwd);
		cookiePwd.setMaxAge(SESSION_OUTTIME);
		cookiePwd.setPath("/");
		response.addCookie(cookiePwd);

		session.setAttribute("User", u);
		session.setMaxInactiveInterval(SESSION_OUTTIME);
		return u.getStatus().getIndex();
	}
	

}
