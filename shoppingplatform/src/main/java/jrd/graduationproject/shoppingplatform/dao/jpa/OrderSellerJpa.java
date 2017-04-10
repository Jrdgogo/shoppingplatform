package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.OrderOfSeller;

public interface OrderSellerJpa extends JpaRepository<OrderOfSeller, String> {

}
