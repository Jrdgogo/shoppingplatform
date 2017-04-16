package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jrd.graduationproject.shoppingplatform.pojo.po.Ware;

public interface WareJpa extends JpaRepository<Ware, String> {

	@Query(value = "UPDATE t_ware SET status=3 WHERE seller=:id", nativeQuery = true)
	int createWareBySeller(String id);

	

}
