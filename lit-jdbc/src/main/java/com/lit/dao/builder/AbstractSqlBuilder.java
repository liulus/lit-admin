package com.lit.dao.builder;

import com.lit.commons.bean.BeanUtils;
import com.lit.dao.model.TableInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * User : liulu
 * Date : 2016-12-3 17:47
 * version $Id: AbstractSqlBuilder.java, v 0.1 Exp $
 */
@NoArgsConstructor
abstract class AbstractSqlBuilder implements SqlBuilder {

    @Getter
    protected TableInfo tableInfo;

    protected Class<?> entityClass;

    protected Map<String, Object> fieldValueMap;

    AbstractSqlBuilder(Class<?> clazz) {
        this.tableInfo = new TableInfo(clazz);
        this.entityClass = clazz;
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
        if (entity == null) {
            return;
        }
        Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();

        for (String fieldName : fieldColumnMap.keySet()) {
            Object obj = BeanUtils.invokeReaderMethod(entity, fieldName);
            if (!isIgnoreNull || obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
                fieldValueMap.put(fieldName, obj);
            }
        }
    }

}
