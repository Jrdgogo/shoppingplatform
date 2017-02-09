package jrd.graduationproject.shoppingplatform.config.scheduling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.util.SpringMail;

@Component
public class SchedulingConfig {
	@Autowired
	public SpringMail springMail;
	
	@Scheduled(initialDelay=1000,fixedDelay=1000*60*60)
    private void process(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("Num", 5);
		springMail.doSend("企查查", "order_failure.ftl", params, "ruidong@greatld.com");
    }

}
