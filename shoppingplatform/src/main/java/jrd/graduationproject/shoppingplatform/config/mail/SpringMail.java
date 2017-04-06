package jrd.graduationproject.shoppingplatform.config.mail;

import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;	

public class SpringMail {
	private JavaMailSender mailSender;
	private TemplateEngine templateEngine;
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
			logger.error("发送邮件失败..", e);
		}
		return false;
	}

	public boolean doSend(String subject, String templateName, Map<String, Object> params, String... receiverUser) {

		try {
			IContext context = new Context();
			context.getVariables().putAll(params);
			String content = templateEngine.process(templateName, context);
			return doSend(subject, content, receiverUser);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
		return false;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(String senderUser) {
		this.senderUser = senderUser;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getPersonal() {
		return personal;
	}

}
