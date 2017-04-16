package jrd.graduationproject.shoppingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ServletComponentScan
public class ShoppingplatformApplication {

	public static void main(String[] args) {
		//org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		SpringApplication.run(ShoppingplatformApplication.class, args);
	}
}
