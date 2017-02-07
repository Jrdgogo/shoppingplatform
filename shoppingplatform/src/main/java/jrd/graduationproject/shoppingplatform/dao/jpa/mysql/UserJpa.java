package jrd.graduationproject.shoppingplatform.dao.jpa.mysql;
import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.User;
public interface UserJpa  extends JpaRepository<User, Integer> {

}
