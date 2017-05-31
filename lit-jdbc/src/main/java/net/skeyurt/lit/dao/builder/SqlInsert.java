package net.skeyurt.lit.dao.builder;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.util.ClassUtils;
import net.skeyurt.lit.dao.enums.GenerationType;
import net.skeyurt.lit.dao.generator.EmptyKeyGenerator;
import net.skeyurt.lit.dao.generator.KeyGenerator;
import net.skeyurt.lit.dao.generator.SequenceGenerator;
import net.skeyurt.lit.dao.model.SqlResult;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * User : liulu
 * Date : 2017/5/30 11:32
 * version $Id: SqlInsert.java, v 0.1 Exp $
 */
public class SqlInsert extends AbstractSqlBuilder {

    private Insert insert = new Insert();

    private List<Column> columns = new ArrayList<>();

    private List<Expression> values = new ArrayList<>();


    SqlInsert(Class<?> clazz) {
        super(clazz);
        insert.setTable(new Table(tableInfo.getTableName()));
        insert.setColumns(columns);
        insert.setItemsList(new ExpressionList(values));
    }

    /**
     * insert 语句的 字段名
     *
     * @param fieldNames
     * @return
     */
    public SqlInsert field(String... fieldNames) {
        for (String fieldName : fieldNames) {
            columns.add(new Column(getColumn(fieldName)));
        }
        return this;
    }

    /**
     * insert 语句的 value值
     *
     * @param values
     * @return
     */
    public SqlInsert values(Object... values) {
        for (Object value : values) {
            this.values.add(PARAM_EXPR);
            params.add(value);
        }
        return this;
    }

    /**
     * 将值直接拼到 insert 语句中，不采用 ? 占位符的方式
     *
     * @param values
     * @return
     */
    public SqlInsert nativeValues(String... values) {
        for (String value : values) {
            this.values.add(new HexValue(value));
        }
        return this;
    }

    public SqlInsert initEntity(Object entity) {
        if (entity == null) {
            return this;
        }
        Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();

        for (Map.Entry<String, String> entry : fieldColumnMap.entrySet()) {
            Object obj = BeanUtils.invokeReaderMethod(entity, entry.getKey());
            if (obj != null && (!(obj instanceof String) || StringUtils.isNotBlank((String) obj))) {
                columns.add(new Column(entry.getValue()));
                values.add(PARAM_EXPR);
                params.add(obj);
            }
        }
        return this;
    }

    @Override
    public SqlResult build() {
        return new SqlResult(insert.toString(), params);
    }


    /**
     * 主键生成器实例的缓存
     */
    private static final Map<String, KeyGenerator> KEY_GENERATOR_CACHE = Collections.synchronizedMap(new HashMap<String, KeyGenerator>());

    private static final SequenceGenerator SEQUENCE_GENERATOR = new SequenceGenerator();

    public KeyGenerator getKeyGenerator() {

        if (Objects.equals(GenerationType.SEQUENCE, tableInfo.getGenerationType())) {
            return SEQUENCE_GENERATOR;
        }

        Class<? extends KeyGenerator> generatorClass = tableInfo.getGeneratorClass();
        if (generatorClass != null && generatorClass != EmptyKeyGenerator.class) {
            KeyGenerator keyGenerator = KEY_GENERATOR_CACHE.get(generatorClass.getName());
            if (keyGenerator == null) {
                keyGenerator = ClassUtils.newInstance(generatorClass);
                KEY_GENERATOR_CACHE.put(generatorClass.getName(), keyGenerator);
            }
            return keyGenerator;
        }

        return null;
    }

    public String getSequenceName() {
        return tableInfo.getSequenceName();
    }

    public boolean isAutoGenerateKey() {
        return tableInfo.isAutoGenerateKey();
    }
}
