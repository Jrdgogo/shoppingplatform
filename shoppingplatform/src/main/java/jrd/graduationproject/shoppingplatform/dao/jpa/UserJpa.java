package jrd.graduationproject.shoppingplatform.dao.jpa;
import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
public interface UserJpa  extends JpaRepository<User, String> {

}
