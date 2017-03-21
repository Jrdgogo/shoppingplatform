package jrd.graduationproject.shoppingplatform.dao.mybatis;

import java.util.List;
import jrd.graduationproject.shoppingplatform.pojo.po.UserExtr;
import jrd.graduationproject.shoppingplatform.pojo.po.UserExtrExample;
import org.apache.ibatis.annotations.Param;

public interface UserExtrMapper {
    int countByExample(UserExtrExample example);

    int deleteByExample(UserExtrExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserExtr record);

    int insertSelective(UserExtr record);

    List<UserExtr> selectByExample(UserExtrExample example);

    UserExtr selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserExtr record, @Param("example") UserExtrExample example);

    int updateByExample(@Param("record") UserExtr record, @Param("example") UserExtrExample example);

    int updateByPrimaryKeySelective(UserExtr record);

    int updateByPrimaryKey(UserExtr record);
}