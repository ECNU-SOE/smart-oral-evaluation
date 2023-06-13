package net.ecnu.aop;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListTypeHandler extends BaseTypeHandler<List<String>> {

    private static final String DELIMITER = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String stringValue = parameter.stream()
                .collect(Collectors.joining(DELIMITER));
        ps.setString(i, stringValue);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String stringValue = rs.getString(columnName);
        return convertStringToList(stringValue);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String stringValue = rs.getString(columnIndex);
        return convertStringToList(stringValue);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String stringValue = cs.getString(columnIndex);
        return convertStringToList(stringValue);
    }

    private List<String> convertStringToList(String stringValue) {
        if (stringValue == null || stringValue.isEmpty()) {
            return null;
        }
        return Arrays.asList(stringValue.split(DELIMITER));
    }
}
