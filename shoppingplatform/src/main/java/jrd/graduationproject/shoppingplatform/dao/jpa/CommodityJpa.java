package jrd.graduationproject.shoppingplatform.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;

public interface CommodityJpa extends JpaRepository<Commodity, String> {

	@Query(value = "SELECT c FROM Commodity c WHERE c.searchkey like %?1%")
	List<Commodity> findBySearchkeyLike(String keyword);

}
