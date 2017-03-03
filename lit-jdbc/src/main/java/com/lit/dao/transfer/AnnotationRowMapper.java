package com.lit.dao.transfer;

import com.lit.commons.bean.BeanUtils;
import com.lit.commons.utils.ClassUtils;
import com.lit.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.lit.dao.annotation.Column;
import com.lit.dao.annotation.Transient;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User : liulu
 * Date : 2016-9-25 16:20
 * version $Id: AnnotationRowMapper.java, v 0.1 Exp $
 */
public class AnnotationRowMapper<T> implements RowMapper<T> {

    private Class<T> mappedClass;

    private Map<String, PropertyDescriptor> mappedFields;

    public AnnotationRowMapper(Class<T> clazz) {
        mappedClass = clazz;
        initialize(clazz);
    }

    private void initialize(Class<T> mappedClass){
        Field[] fields = mappedClass.getDeclaredFields();
        mappedFields = new HashMap<>(fields.length);

        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(mappedClass, field.getName());
            if (pd != null && pd.getReadMethod() != null && pd.getWriteMethod() != null) {
                mappedFields.put(column != null ? column.name() : NameUtils.getUnderLineName(field.getName()), pd);
            }
        }
    }


    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {

        T result = ClassUtils.newInstance(mappedClass);

        ResultSetMetaData rsMetaData = rs.getMetaData();
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            String column = StringUtils.trim(JdbcUtils.lookupColumnName(rsMetaData, i));
            PropertyDescriptor pd = this.mappedFields.get(column);
            if (pd != null) {
                Object value = JdbcUtils.getResultSetValue(rs, i, pd.getPropertyType());
                ClassUtils.invokeMethod(pd.getWriteMethod(), result, value);
            }
        }
        return result;
    }

}
