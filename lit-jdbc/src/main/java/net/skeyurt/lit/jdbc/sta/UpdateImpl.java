package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.schema.Column;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.jdbc.model.StatementContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/6/4 9:35
 * version $Id: UpdateImpl.java, v 0.1 Exp $
 */
class UpdateImpl extends AbstractCondition<Update> implements Update{

    protected static final Expression NULL_EXPR = new HexValue("null");

    private net.sf.jsqlparser.statement.update.Update update;

    private List<Column> columns;

    private List<Expression> values;

    UpdateImpl(Class<?> clazz) {
        super(clazz);
        update = new net.sf.jsqlparser.statement.update.Update();
        columns = new ArrayList<>();
        values = new ArrayList<>();
        update.setTables(Collections.singletonList(table));
        update.setColumns(columns);
        update.setExpressions(values);
    }

    @Override
    public Update set(String... fieldNames) {
        for (String fieldName : fieldNames) {
            columns.add(new Column(getColumn(fieldName)));
        }
        return this;
    }

    @Override
    public Update values(Object... values) {
        for (Object value : values) {
            this.values.add(PARAM_EXPR);
            params.add(value);
        }
        return this;
    }


    @Override
    public Update initEntity(Object entity, boolean isIgnoreNull) {
        if (entity == null) {
            return this;
        }
        Object key = BeanUtils.invokeReaderMethod(entity, tableInfo.getPkField());
        if (key == null) {
            throw new NullPointerException("entity [" + entity + "] id is null, can not update!");
        }

        Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();

        for (Map.Entry<String, String> entry : fieldColumnMap.entrySet()) {
            if (StringUtils.equals(tableInfo.getPkField(), entry.getKey())) {
                continue;
            }

            Object obj = BeanUtils.invokeReaderMethod(entity, entry.getKey());
            if (!isIgnoreNull || obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
                columns.add(new Column(entry.getValue()));
                if (obj == null) {
                    values.add(NULL_EXPR);
                } else {
                    values.add(PARAM_EXPR);
                    params.add(obj);
                }

            }
        }
        idCondition(key);

        return this;
    }

    @Override
    public int execute() {
        update.setWhere(where);
        return (int) executor.execute(new StatementContext(update.toString(), params, StatementContext.StatementType.UPDATE));
    }
}
