package jrd.graduationproject.shoppingplatform.config.mail;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import jrd.graduationproject.shoppingplatform.config.redis.JedisDataSource;
import jrd.graduationproject.shoppingplatform.util.PropertiesUtil;

@Configuration
@PropertySource(value = { "classpath:mail.properties" })
public class SpringMailConfig {

	@Autowired
	private Environment env;

	@Bean(name = "mailTemplateEngine")
	public TemplateEngine templateEngine() {

		TemplateEngine engine = new SpringTemplateEngine();
		TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix(env.getProperty("mail_prefix"));
		templateResolver.setSuffix(env.getProperty("mail_suffix"));
		templateResolver.setTemplateMode(env.getProperty("mail_model"));
		templateResolver.setCharacterEncoding(env.getProperty("mail_defaultEncoding"));
		engine.setTemplateResolver(templateResolver);
		return engine;
	}

	@Bean(name = "mailSender")
	public JavaMailSender getSender(@Qualifier(value = "mailProperties") Properties properties) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getProperty("mail_host"));
		javaMailSender.setPort(env.getProperty("mail_port", int.class));
		javaMailSender.setUsername(env.getProperty("mail_username"));
		javaMailSender.setPassword(env.getProperty("mail_password"));
		javaMailSender.setDefaultEncoding(env.getProperty("mail_defaultEncoding"));
		javaMailSender.setJavaMailProperties(properties);
		return javaMailSender;
	}

	private static final String mailPropertiesStartsWith = "spring.mailProperties";

	@Bean(name = "mailProperties")
	public Properties mailProperties() throws IOException {
		return PropertiesUtil.propertiesOfFile("mail.properties", mailPropertiesStartsWith);
	}

	@Bean(name = "SpringMail")
	public SpringMail springMail(@Qualifier("mailSender") JavaMailSender mailSender,
			@Qualifier("mailTemplateEngine") TemplateEngine templateEngine) throws IOException {
		SpringMail springMail = new SpringMail();
		springMail.setMailSender(mailSender);
		springMail.setTemplateEngine(templateEngine);
		springMail.setSenderUser(env.getProperty("mail_senderUser"));
		springMail.setPersonal(env.getProperty("mail_personal"));
		return springMail;
	}

	@Bean(name = "MailSubscribe", initMethod = "listener")
	public MailSubscribe mailSubscribe(@Qualifier("SpringMail") SpringMail springMail,
			@Qualifier("jedisDataSource") JedisDataSource jedisDataSource) throws IOException {
		MailSubscribe mailSubscribe = new MailSubscribe();
		mailSubscribe.setSpringMail(springMail);
		mailSubscribe.setJedis(jedisDataSource.getJedis());
		return mailSubscribe;
	}

	@Bean(name = "MailPublish")
	public MailPublish mailPublish(@Qualifier("SpringMail") SpringMail springMail,
			@Qualifier("jedisDataSource") JedisDataSource jedisDataSource) throws IOException {
		MailPublish mailPublish = new MailPublish();
		mailPublish.setJedisDataSource(jedisDataSource);
		return mailPublish;
	}
}
