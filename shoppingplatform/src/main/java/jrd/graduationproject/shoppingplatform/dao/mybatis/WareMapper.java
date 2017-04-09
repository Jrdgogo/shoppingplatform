package jrd.graduationproject.shoppingplatform.dao.mybatis;

import java.util.List;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.po.WareExample;
import org.apache.ibatis.annotations.Param;

public interface WareMapper {
    int countByExample(WareExample example);

    int deleteByExample(WareExample example);

    int deleteByPrimaryKey(String id);

    int insert(Ware record);

    int insertSelective(Ware record);

    List<Ware> selectByExample(WareExample example);

    Ware selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Ware record, @Param("example") WareExample example);

    int updateByExample(@Param("record") Ware record, @Param("example") WareExample example);

    int updateByPrimaryKeySelective(Ware record);

    int updateByPrimaryKey(Ware record);
}