package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;

public class OrderStatusEnumTypeHandler extends BaseTypeHandler<OrderStatusEnum> {
	
	private Class<OrderStatusEnum> type;
	
	public OrderStatusEnumTypeHandler(Class<OrderStatusEnum> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, OrderStatusEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getIndex());
		
	}
	
	@Override
	public OrderStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return OrderStatusEnum.getStatusByIndex(i);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
			}
		}
	}
	
	@Override
	public OrderStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return OrderStatusEnum.getStatusByIndex(i);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
			}
		}
	}
	
	@Override
	public OrderStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			try {
				return OrderStatusEnum.getStatusByIndex(i);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by ordinal value.", ex);
			}
		}
	}
	
	
}
