package jrd.graduationproject.shoppingplatform.config.scheduling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import jrd.graduationproject.shoppingplatform.config.mail.SpringMail;

//@Component
public class SchedulingConfig {
	@Autowired
	public SpringMail springMail;

	@Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60)
	private void process() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Num", 5);
		springMail.doSend("只为让您购物愉快", "order_failure.ftl", params, "1477450172@qq.com");
	}

}
