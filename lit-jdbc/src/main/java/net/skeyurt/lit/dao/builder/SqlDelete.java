package net.skeyurt.lit.dao.builder;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017/5/30 12:09
 * version $Id: SqlDelete.java, v 0.1 Exp $
 */
public class SqlDelete extends SqlWhere {

    private Delete delete = new Delete();


    SqlDelete(Class<?> clazz) {
        super(clazz);
        delete.setTable(new Table(tableInfo.getTableName()));
    }

    /**
     * 添加 where 条件，默认操作符 =
     *
     * @param fieldName 属性名
     * @param value     值
     * @return
     */
    public SqlDelete where(String fieldName, Object value) {
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
    public SqlDelete where(String fieldName, Operator operator, Object... values) {
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
    public SqlDelete and(String fieldName, Object value) {
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
    public SqlDelete and(String fieldName, Operator operator, Object... values) {
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
    public SqlDelete andWithBracket(String fieldName, Object value) {
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
    public SqlDelete andWithBracket(String fieldName, Operator operator, Object... values) {
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
    public SqlDelete or(String fieldName, Object value) {
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
    public SqlDelete or(String fieldName, Operator operator, Object... values) {
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
    public SqlDelete orWithBracket(String fieldName, Object value) {
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
    public SqlDelete orWithBracket(String fieldName, Operator operator, Object... values) {
        addExpr(fieldName, operator, false, true, values);
        return this;
    }

    /**
     * 添加 where 条件中的结束 括号
     *
     * @return
     */
    public SqlDelete end() {
        addEnd();
        return this;
    }


    @Override
    public SqlResult build() {
        delete.setWhere(where);
        return new SqlResult(delete.toString(), params);
    }

    public SqlDelete initEntity(Object entity) {
        if (entity == null) {
            throw new NullPointerException("entity is null, can not delete!");
        }
        Object obj = BeanUtils.invokeReaderMethod(entity, tableInfo.getPkField());
        if (obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
            where(tableInfo.getPkField(), obj);
        } else {
            throw new NullPointerException("entity [" + entity + "] id is null, can not delete!");
        }
        return this;
    }
}
