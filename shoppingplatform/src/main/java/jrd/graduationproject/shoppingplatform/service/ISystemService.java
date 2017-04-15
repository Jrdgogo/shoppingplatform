package jrd.graduationproject.shoppingplatform.service;

import org.springframework.data.domain.Page;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface ISystemService {

	User grantAdmin(User admin, User user);

	User cancelAdmin(User admin, User user);

	Seller grantSeller(Seller user);

	void cancelSeller(Seller user);

	User freezeUser(User user);

	Page<Message> selectMessage(PageParam page, String id);

	Long countMessage(String id);

	Message handleMessage(String id);

	Page<User> SelectUserByType(AdminEnum adminEnum, PageParam page, User user);

	User activeUser(User user);

	Boolean apply(Integer type, String id);

	Page<Seller> SelectSeller(PageParam page, Seller seller);
}
