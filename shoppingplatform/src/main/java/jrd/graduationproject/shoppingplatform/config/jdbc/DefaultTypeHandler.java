package jrd.graduationproject.shoppingplatform.config.jdbc;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import jrd.graduationproject.shoppingplatform.util.MybatisTypeHandlerUtil;

public class DefaultTypeHandler<E> extends BaseTypeHandler<E> {

	private Class<E> type;

	public DefaultTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {

		ps.setObject(i, MybatisTypeHandlerUtil.getid(parameter));

	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Object id = rs.getObject(columnName);

		return MybatisTypeHandlerUtil.newInstance(id, type);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Object id = rs.getObject(columnIndex);

		return MybatisTypeHandlerUtil.newInstance(id, type);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Object id = cs.getObject(columnIndex);

		return MybatisTypeHandlerUtil.newInstance(id, type);
	}

}
