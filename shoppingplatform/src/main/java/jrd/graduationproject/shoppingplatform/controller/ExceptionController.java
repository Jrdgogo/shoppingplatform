package jrd.graduationproject.shoppingplatform.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;

@ControllerAdvice
public class ExceptionController {

	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(value = { Exception.class })
	@ResponseBody
	public String ExceptionHandle(Exception ex, HttpServletResponse response) {
		Class<? extends Exception> cls=ex.getClass(); 
		
		String msg=ex.getMessage();
		if(UserOptionErrorException.class.isAssignableFrom(cls)){
			response.setStatus(430);
			logger.info("用户操作错误："+msg);
			return msg;
		}
		response.setStatus(431);
		logger.error("程序出现异常",ex);
		return msg;
	}
}
