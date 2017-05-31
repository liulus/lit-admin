package net.skeyurt.lit.dao.builder;

import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.skeyurt.lit.dao.model.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User : liulu
 * Date : 2016-12-3 17:47
 * version $Id: AbstractSqlBuilder.java, v 0.1 Exp $
 */
@NoArgsConstructor
abstract class AbstractSqlBuilder implements SqlBuilder {

    protected static final Expression PARAM_EXPR = new JdbcParameter();

    protected TableInfo tableInfo;

    protected List<Object> params = new ArrayList<>();

    AbstractSqlBuilder(Class<?> clazz) {
        this.tableInfo = new TableInfo(clazz);
    }

    public String getColumn(String fieldName) {
        fieldName = StringUtils.trim(fieldName);
        String column = tableInfo.getFieldColumnMap().get(fieldName);
        return StringUtils.isEmpty(column) ? fieldName : column;
    }

    public String getPkName () {
        return tableInfo.getPkField();
    }


}
