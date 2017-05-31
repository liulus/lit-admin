package net.skeyurt.lit.dao.builder;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/5/30 12:09
 * version $Id: SqlUpdate.java, v 0.1 Exp $
 */
public class SqlUpdate extends SqlWhere {

    private Update update = new Update();

    private List<Column> columns = new ArrayList<>();

    private List<Expression> values = new ArrayList<>();

    SqlUpdate(Class<?> clazz) {
        super(clazz);
        update.setTables(Collections.singletonList(new Table(tableInfo.getTableName())));
        update.setColumns(columns);
        update.setExpressions(values);
    }

    /**
     * update 语句中的 set 字段
     *
     * @param fieldNames
     * @return
     */
    public SqlUpdate set(String... fieldNames) {
        for (String fieldName : fieldNames) {
            columns.add(new Column(getColumn(fieldName)));
        }
        return this;
    }

    /**
     * set 字段对应 的值
     *
     * @param values
     * @return
     */
    public SqlUpdate values(Object... values) {
        for (Object value : values) {
            this.values.add(PARAM_EXPR);
            params.add(value);
        }
        return this;
    }

    /**
     * 添加 where 条件，默认操作符 =
     *
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    public SqlUpdate where(String fieldName, Object value) {
        addExpr(fieldName, Operator.EQ, true, false, value);
        return this;
    }

    /**
     * 添加 where 条件
     *
     * @param fieldName
     * @param operator
     * @param values
     * @return
     */
    public SqlUpdate where(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return this;
    }

    /**
     * 添加 and 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlUpdate and(String fieldName, Object value) {
        this.and(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlUpdate and(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, false, values);
        return this;
    }

    /**
     * 添加带 括号 的 and 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlUpdate andWithBracket(String fieldName, Object value) {
        this.andWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 and 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlUpdate andWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, true, true, values);
        return this;
    }

    /**
     * 添加 or 条件，默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlUpdate or(String fieldName, Object value) {
        this.or(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlUpdate or(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, false, values);
        return this;
    }

    /**
     * 添加带 括号 的 or 条件， 默认操作符 =
     *
     * @param fieldName
     * @param value
     * @return
     */
    public SqlUpdate orWithBracket(String fieldName, Object value) {
        this.orWithBracket(fieldName, Operator.EQ, value);
        return this;
    }

    /**
     * 添加 括号 的 or 条件，
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SqlUpdate orWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, true, values);
        return this;
    }

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    public SqlUpdate end() {
        addEnd();
        return this;
    }

    public SqlUpdate initEntity (Object entity) {
        return initEntity(entity, true);
    }

    public SqlUpdate initEntity(Object entity, boolean isIgnoreNull) {
        if (entity == null) {
            return this;
        }
        Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();

        for (Map.Entry<String, String> entry : fieldColumnMap.entrySet()) {
            Object obj = BeanUtils.invokeReaderMethod(entity, entry.getKey());
            if (!isIgnoreNull || obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
                columns.add(new Column(entry.getValue()));
                values.add(PARAM_EXPR);
                params.add(obj);
            }
        }

        return this;
    }


    @Override
    public SqlResult build() {
        update.setWhere(where);
        return new SqlResult(update.toString(), params);
    }
}
