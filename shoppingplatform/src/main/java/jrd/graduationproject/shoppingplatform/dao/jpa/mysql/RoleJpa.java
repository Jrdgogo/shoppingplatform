package jrd.graduationproject.shoppingplatform.dao.jpa.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;

public interface RoleJpa extends JpaRepository<Role, AdminEnum> {

}
