package jrd.graduationproject.shoppingplatform.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import jrd.graduationproject.shoppingplatform.pojo.po.Comment;

public interface CommentJpa extends JpaRepository<Comment, String> {

}
