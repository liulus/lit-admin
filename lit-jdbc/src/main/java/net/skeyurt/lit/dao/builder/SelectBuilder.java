package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User : liulu
 * Date : 2016-12-3 18:59
 * version $Id: SelectBuilder.java, v 0.1 Exp $
 */
class SelectBuilder extends AbstractSqlBuilder {

    private WhereBuilder whereBuilder;

    private OrderByBuilder orderByBuilder;

    private StringBuilder include;

    private StringBuilder func;

    private Map<String, String> defaultFields;

    SelectBuilder(Class<?> clazz) {
        super(clazz);
        whereBuilder = new WhereBuilder(tableInfo);
        defaultFields = new HashMap<>(tableInfo.getFieldColumnMap());
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
    }

    @Override
    public void add(String logicOperator, String fieldName, Operator fieldOperator, FieldType fieldType, Object... values) {
        switch (fieldType) {
            case WHERE:
            case BRACKET_BEGIN:
            case BRACKET_END:
                whereBuilder.add(logicOperator, fieldName, fieldOperator, fieldType, values);
                break;
            case INCLUDE:
                String column = getColumn(fieldName);
                if (include == null) {
                    include = new StringBuilder();
                    include.append(column);
                } else {
                    include.append(", ").append(column);
                }
                break;
            case EXCLUDE:
                defaultFields.remove(StringUtils.trim(fieldName));
                break;
            case FUNC:
                String funStr = parseFunc(fieldName);
                if (func == null) {
                    func = new StringBuilder(funStr.length());
                }
                if (func.indexOf(funStr) < 0) {
                    func.append(StringUtils.isEmpty(func) ? "" : ", ").append(funStr);
                }
                break;
        }
    }

    private String parseFunc(String funcStr) {
        if (StringUtils.contains(funcStr, '(') && StringUtils.contains(funcStr, ')')) {
            int start = StringUtils.indexOf(funcStr, '(') + 1;
            int end = StringUtils.indexOf(funcStr, ')');
            String column = getColumn(StringUtils.substring(funcStr, start, end));
            return StringUtils.substring(funcStr, 0, start) + column + ')';
        }
        return funcStr;
    }

    @Override
    public SqlResult build() {
        StringBuilder sql = new StringBuilder("select ");
        if (StringUtils.isNotEmpty(func)) {
            sql.append(func);
        } else if (StringUtils.isNotEmpty(include)) {
            sql.append(include);
        } else {
            sql.append(StringUtils.join(defaultFields.values(), ", "));
        }
        SqlResult whereResult = whereBuilder.build();
        sql.append(" from ").append(tableInfo.getTableName()).append(whereResult.getSql());
        if (StringUtils.isEmpty(func) && orderByBuilder != null) {
            SqlResult orderByResult = orderByBuilder.build();
            sql.append(orderByResult.getSql());
        }
        return new SqlResult(sql.toString(), new ArrayList<>(whereResult.getParams()));
    }

}
