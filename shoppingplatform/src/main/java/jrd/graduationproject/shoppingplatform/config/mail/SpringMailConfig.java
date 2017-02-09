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
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.TemplateException;
import jrd.graduationproject.shoppingplatform.util.SpringMail;

@Configuration
@PropertySource(value = { "classpath:mail.properties" })
public class SpringMailConfig {

	@Autowired
	private Environment env;

	@Bean(name = "freemarkerConfiguration")
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
		FreeMarkerConfigurationFactoryBean freeMarkerConfiguration = new FreeMarkerConfigurationFactoryBean();
		freeMarkerConfiguration.setTemplateLoaderPath(env.getProperty("templateLoaderPath"));
		return freeMarkerConfiguration;
	}

	@Bean(name = "mailSender")
	public JavaMailSender getSender(@Qualifier(value="mailProperties")Properties properties) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getProperty("host"));
		javaMailSender.setPort(env.getProperty("port",int.class));
		javaMailSender.setUsername(env.getProperty("username"));
		javaMailSender.setPassword(env.getProperty("password"));
		javaMailSender.setDefaultEncoding(env.getProperty("defaultEncoding"));
		javaMailSender.setJavaMailProperties(properties);
		return javaMailSender;
	}
	private static final String mailPropertiesStartsWith="spring.mailProperties";
	@Bean(name = "mailProperties")
	public Properties mailProperties() throws IOException {
		Properties properties= new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
		Properties mailProperties= new Properties();
		for(String nameKey:properties.stringPropertyNames()){
			if(nameKey.startsWith(mailPropertiesStartsWith))
				mailProperties.setProperty(nameKey.substring(mailPropertiesStartsWith.length()+1), properties.getProperty(nameKey));
		}
		return mailProperties;
	}
	@Bean(name = "SpringMail")
	public SpringMail springMail(@Qualifier("mailSender")JavaMailSender mailSender,@Qualifier("freemarkerConfiguration")FreeMarkerConfigurationFactoryBean freemarkerConfiguration) throws IOException, TemplateException {
		SpringMail springMail= new SpringMail();
		springMail.setMailSender(mailSender);
		springMail.setFreemarkerConfiguration(freemarkerConfiguration.createConfiguration());
		springMail.setSenderUser(env.getProperty("senderUser"));
		springMail.setPersonal(env.getProperty("personal"));
		return springMail;
	}
}
