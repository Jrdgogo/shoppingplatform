package jrd.graduationproject.shoppingplatform.dao.mybatis;

import java.util.List;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCarExample;
import org.apache.ibatis.annotations.Param;

public interface ShopCarMapper {
    int countByExample(ShopCarExample example);

    int deleteByExample(ShopCarExample example);

    int deleteByPrimaryKey(String id);

    int insert(ShopCar record);

    int insertSelective(ShopCar record);

    List<ShopCar> selectByExample(ShopCarExample example);

    ShopCar selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ShopCar record, @Param("example") ShopCarExample example);

    int updateByExample(@Param("record") ShopCar record, @Param("example") ShopCarExample example);

    int updateByPrimaryKeySelective(ShopCar record);

    int updateByPrimaryKey(ShopCar record);
}