package net.skeyurt.lit.dao.builder;

import net.skeyurt.lit.dao.enums.FieldType;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import net.skeyurt.lit.dao.model.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User : liulu
 * Date : 2016-12-4 20:33
 * version $Id: WhereBuilder.java, v 0.1 Exp $
 */
class WhereBuilder extends AbstractSqlBuilder {

    private StringBuilder whereSql;

    private List<Object> params;


    WhereBuilder(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        whereSql = new StringBuilder();
        params = new ArrayList<>();
    }

    @Override
    public void initEntity(Object entity, Boolean isIgnoreNull) {
    }

    @Override
    public void add(String logicOperator, String fieldName, Operator fieldOperator, FieldType fieldType, Object... values) {
        String columnName = getColumn(fieldName);
        if (fieldType == FieldType.BRACKET_END) {
            whereSql.append(") ");
        } else if (StringUtils.contains(fieldOperator.getValue(), "null")) {
            whereSql.append(logicOperator).append(columnName).append(fieldOperator.getValue());
        } else if (StringUtils.contains(fieldOperator.getValue(), "in")) {
            whereSql.append(logicOperator).append(columnName).append(fieldOperator.getValue()).append("( ");
            for (Object obj : values) {
                whereSql.append("?, ");
                this.params.add(obj);
            }
            whereSql.deleteCharAt(whereSql.lastIndexOf(",")).append(") ");
        } else if (values == null || values.length == 0 || values[0] == null) {
            whereSql.append(logicOperator).append(columnName).append(" is null ");
        }else {
            whereSql.append(logicOperator).append(columnName).append(fieldOperator.getValue()).append("? ");
            this.params.add(values[0]);
        }
    }

    @Override
    public SqlResult build() {
        if (StringUtils.isEmpty(whereSql)) {
            return SqlResult.EMPTY_RESULT;
        }
        String whereStr = StringUtils.removeStart(StringUtils.removeStart(whereSql.toString(), "and"), "or");
        return new SqlResult(" where " + whereStr, this.params);
    }


}
