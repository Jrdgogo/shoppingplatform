package jrd.graduationproject.shoppingplatform.dao.mybatis;

import java.util.List;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddrExample;
import org.apache.ibatis.annotations.Param;

public interface UserWareAddrMapper {
    int countByExample(UserWareAddrExample example);

    int deleteByExample(UserWareAddrExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserWareAddr record);

    int insertSelective(UserWareAddr record);

    List<UserWareAddr> selectByExample(UserWareAddrExample example);

    UserWareAddr selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserWareAddr record, @Param("example") UserWareAddrExample example);

    int updateByExample(@Param("record") UserWareAddr record, @Param("example") UserWareAddrExample example);

    int updateByPrimaryKeySelective(UserWareAddr record);

    int updateByPrimaryKey(UserWareAddr record);
}