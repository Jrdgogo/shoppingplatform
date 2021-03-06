package jrd.graduationproject;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jrd.graduationproject.shoppingplatform.ShoppingplatformApplication;
import jrd.graduationproject.shoppingplatform.config.redis.JedisDataSource;
import jrd.graduationproject.shoppingplatform.controller.HomeController;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserWareAddrMapper;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShoppingplatformApplication.class)
public class ShoppingplatformApplicationTests {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserWareAddrMapper userWareAddrMapper;

	@Test
	public void idHandle() {
		UserWareAddr userWareAddr=userWareAddrMapper.selectByPrimaryKey("342623J19950718R0302D");
	    System.out.println(userWareAddr.getUser().getId());
	}
	@Test
	public void service() {
		User user = new User();
		user.setUsername("redisMail");
		user.setPassword("redisMail_J");
		user.setEmail("1477450172@qq.com");

		user = userService.RegisterUser(user);

		System.out.println(user.getId());

		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Autowired
	private JedisDataSource jedisDataSource;

	@Test
	public void jedis() {
		Jedis jedis = jedisDataSource.getJedis();
		System.out.println(">>>>>>" + jedis.set("name1", "vale", "xx", "ex", 10));
		System.out.println(">>>>>>" + jedis.set("name2", "vale"));
		System.out.println(">>>>>>" + jedis.set("name3", "vale", "nx", "ex", 10));
		System.out.println(">>>>>>" + jedis.set("name4", "vale", "nx", "px", 10));
		System.out.println(">>>>>>" + jedis.set("name5", "vale", "xx", "px", 10));
	}
	@Test
	public void photo() {
		String root = "/images/wares/";
		String path=Thread.currentThread().getContextClassLoader().getResource("static"+root).getFile();
		
		File savediv = new File(path, "af");
        if(!savediv.exists())
           savediv.mkdirs();
		System.out.println(savediv.getAbsolutePath());
		
	}
	
	@Autowired
	private HomeController controller;
	@Test
	public void activeUser(){
		try {
			String msg=controller.ActivationUser("5c5df2124263913faa902805cf3100eb", "f664c76836f54894bba0e7852070523c");
			 System.out.println(msg);
		} catch (Exception e1) {
			
		}
	     
	     try {
				Thread.sleep(1000 * 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	public void jpaMybatis(){
		
		try {
			User user=new User();
			user.setId("342623J19950718R0302D");
			user.setAccount(48625.0);
			user=userService.alterUserInfo(user);
			System.out.println(user.getAccount());
			//System.out.println(userService.getUserById(user.getId()).getAccount());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(1000 * 100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
