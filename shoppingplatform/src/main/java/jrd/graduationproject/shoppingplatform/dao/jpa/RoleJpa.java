package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Role;

public interface RoleJpa extends JpaRepository<Role, AdminEnum> {

}
