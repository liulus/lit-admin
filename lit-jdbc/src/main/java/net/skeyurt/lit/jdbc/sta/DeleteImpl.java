package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.schema.Table;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.jdbc.model.StatementContext;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017/6/4 9:51
 * version $Id: DeleteImpl.java, v 0.1 Exp $
 */
class DeleteImpl extends AbstractCondition<Delete> implements Delete {

    private net.sf.jsqlparser.statement.delete.Delete delete = new net.sf.jsqlparser.statement.delete.Delete();


    DeleteImpl(Class<?> clazz) {
        super(clazz);
        delete.setTable(new Table(tableInfo.getTableName()));
    }

    @Override
    public DeleteImpl initEntity(Object entity) {
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

    @Override
    public int execute() {
        delete.setWhere(where);
        return (int) executor.execute(new StatementContext(delete.toString(), params, StatementContext.StatementType.DELETE));
    }
}
