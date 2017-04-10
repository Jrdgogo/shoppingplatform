package jrd.graduationproject.shoppingplatform.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jrd.graduationproject.shoppingplatform.pojo.po.Message;

public interface MessageJpa extends JpaRepository<Message, String> {

	@Query(value = "SELECT * FROM t_message ORDER BY CASE status WHEN 0 THEN 0 ELSE 1 END,update DESC LIMIT :pagenum,:pagesize", nativeQuery = true)
	List<Message> findAllByDefault(Integer pagenum, Integer pagesize);


}
