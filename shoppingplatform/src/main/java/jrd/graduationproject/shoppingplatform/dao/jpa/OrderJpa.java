package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;

public interface OrderJpa extends JpaRepository<Order, String> {

	

}
