package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;

public interface CommodityJpa extends JpaRepository<Commodity, String> {

	

}
