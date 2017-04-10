package jrd.graduationproject.shoppingplatform.service;

import org.springframework.data.domain.Page;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface ISystemService {
	
	User grantAdmin(User admin,User user);
	
	User cancelAdmin(User admin,User user);
	
	User grantSeller(User user);
	
	User cancelSeller(User user);
	
	User freezeUser(User user);

	Page<Message> selectMessage(PageParam page, String id);

	Message handleMessage(String id);

	Page<User> SelectUserByType(AdminEnum adminEnum, PageParam page);

	User activeUser(User user);
}
