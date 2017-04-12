package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;

public interface IUserService {

	User getUserByName_Pwd(User user);

	User getUserByName(String username);

	User ActivationUser(String id, String activeCode);

	User RegisterUser(User user);

	User alterUserInfo(User user);

	User getUserInfo(String id);

	List<ShopCar> getUserShopCar(String id);

	ShopCar getaddShopCar(String userid, Ware ware);

	void removeShopCar(ShopCar shopCar);
}
