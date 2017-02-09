package jrd.graduationproject.shoppingplatform.util;

import java.io.IOException;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SpringMail {
	private JavaMailSender mailSender;
	private Configuration freemarkerConfiguration;
	private static Logger logger = LoggerFactory.getLogger(SpringMail.class);
	private String senderUser;
	private String personal;
	
	private static final String DEFAULT_ENCODING = "utf-8";
	
	public boolean doSend(String subject, String content, String... receiverUser) {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			helper.setTo(receiverUser);
			helper.setFrom(new InternetAddress(senderUser, personal));
			
			helper.setSubject(subject);
			helper.setText(content, true);
			getMailSender().send(msg);
			logger.info("发送邮件成功.");
			return true;
		} catch (Exception e) {
			logger.error("发送邮件失败..",e);
		}
		return false;
	}
	
	public boolean doSend(String subject, String templateName, Map<String, Object> params, String... receiverUser){
		try {
			Template template = freemarkerConfiguration.getTemplate(templateName, DEFAULT_ENCODING);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
			return doSend(subject, content, receiverUser);
		} catch (IOException e) {
			logger.error("无法获取邮件模板，发送邮件失败",e);
		} catch (TemplateException e) {
			logger.error("邮件模板参数错误，发送邮件失败",e);
		} catch (Exception e) {
			logger.error("发送邮件失败",e);
		}
		return false;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public Configuration getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public String getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(String senderUser) {
		this.senderUser = senderUser;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}
	

}
