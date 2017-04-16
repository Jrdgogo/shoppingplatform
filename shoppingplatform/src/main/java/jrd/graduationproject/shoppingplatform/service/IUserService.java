package jrd.graduationproject.shoppingplatform.service;

import java.util.List;
import java.util.Set;

import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;

public interface IUserService {

	User getUserByName_Pwd(User user);

	User getUserByName(String username);

	User ActivationUser(String id, String activeCode);

	User RegisterUser(User user);

	User alterUserInfo(User user);

	User getUserInfo(String id);

	List<ShopCar> getUserShopCar(String id);

	ShopCar getaddShopCar(String userid, Ware ware, Integer num);

	void removeShopCar(List<String> shopcars);

	UserWareAddr addAddr(String id, UserWareAddr addr);

	UserWareAddr alterAddr(UserWareAddr addr);

	Set<UserWareAddr> getAllAddr(String id);

	List<ShopCar> getUserShopCar(List<String> shopcars);

	void applySeller(Seller seller, String id);

	User getUserByName_cookiePwd(User user);
}
