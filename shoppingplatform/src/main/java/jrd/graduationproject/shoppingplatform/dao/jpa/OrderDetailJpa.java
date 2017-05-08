package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.OrderDetail;

public interface OrderDetailJpa extends JpaRepository<OrderDetail, String> {

	

}
