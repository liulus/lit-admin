package net.skeyurt.lit.dao.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.dao.model.TableInfo;
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

    protected Map<String, Object> columnValueMap;

    protected Map<String, Object> nativeValueMap;

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
                columnValueMap.put(fieldColumnMap.get(fieldName), obj);
            }
        }
    }

    protected String getColumn(String fieldName) {
        fieldName = StringUtils.trim(fieldName);
        String column = tableInfo.getFieldColumnMap().get(fieldName);
        return StringUtils.isEmpty(column) ? fieldName : column;
    }


}
