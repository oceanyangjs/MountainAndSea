package com.mountain.sea.core.mybatis.handler.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 16:01
 */
public class GeneralTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {
    E[] enums;

    public GeneralTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.enums = type.getEnumConstants();
            if (this.enums == null) {
                throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String i = rs.getString(columnName);
        return rs.wasNull() ? null : this.locateEnum(i);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String i = rs.getString(columnIndex);
        return rs.wasNull() ? null : this.locateEnum(i);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String i = cs.getString(columnIndex);
        return cs.wasNull() ? null : this.locateEnum(i);
    }

    private E locateEnum(String value) {
        BaseEnum[] var2 = this.enums;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            E status = (E) var2[var4];
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("未知的枚举类型：" + value);
    }
}
