package com.lit.dao.builder;

import com.lit.commons.bean.BeanUtils;
import com.lit.commons.utils.NameUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * User : liulu
 * Date : 2016-10-4 15:42
 */
class TableInfo {

    private String tableName;

    private String pkField;

    private String pkColumn;

    private Map<String, String> fieldColumnMap;

    TableInfo(Class clazz) {
        fieldColumnMap = new HashMap<>();
        initTableInfo(clazz);
    }

    /**
     * 根据注解初始化表信息，
     *
     * @param clazz
     */
    private void initTableInfo(Class clazz) {
        tableName = clazz.isAnnotationPresent(Table.class) ? ((Table) clazz.getAnnotation(Table.class)).name() : NameUtils.getUnderLineName(clazz.getSimpleName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            if (field.isAnnotationPresent(Id.class) || (StringUtils.equalsIgnoreCase(field.getName(), clazz.getSimpleName() + "Id") && pkField == null)) {
                pkField = field.getName();
                pkColumn = column != null ? column.name() : NameUtils.getUnderLineName(field.getName());
            }
            PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());
            if (descriptor != null && descriptor.getReadMethod() != null && descriptor.getWriteMethod() != null) {
                fieldColumnMap.put(field.getName(), column != null ? column.name() : NameUtils.getUnderLineName(field.getName()));
            }
        }
    }

    String getTableName() {
        return tableName;
    }

    String getPkField() {
        return pkField;
    }

    String getPkColumn() {
        return pkColumn;
    }

    Map<String, String> getFieldColumnMap() {
        return fieldColumnMap;
    }

}
