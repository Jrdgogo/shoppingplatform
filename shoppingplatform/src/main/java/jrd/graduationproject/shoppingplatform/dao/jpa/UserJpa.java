package jrd.graduationproject.shoppingplatform.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jrd.graduationproject.shoppingplatform.pojo.po.User;

public interface UserJpa extends JpaRepository<User, String> {

	User findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);

	@Query(value = "SELECT * FROM t_user", nativeQuery = true)
	List<User> cacheFlush();

}
