package jrd.graduationproject.shoppingplatform.dao.jpa;
import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
public interface SellerJpa  extends JpaRepository<Seller, String> {

}
