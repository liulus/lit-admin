package net.skeyurt.lit.jdbc.sta;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.util.ClassUtils;
import net.skeyurt.lit.jdbc.enums.GenerationType;
import net.skeyurt.lit.jdbc.generator.EmptyKeyGenerator;
import net.skeyurt.lit.jdbc.generator.KeyGenerator;
import net.skeyurt.lit.jdbc.generator.SequenceGenerator;
import net.skeyurt.lit.jdbc.model.StatementContext;

import java.io.Serializable;
import java.util.*;

/**
 * User : liulu
 * Date : 2017/6/4 9:53
 * version $Id: InsertImpl.java, v 0.1 Exp $
 */
class InsertImpl extends AbstractStatement implements Insert {

    private net.sf.jsqlparser.statement.insert.Insert insert;

    private List<Column> columns = new ArrayList<>();

    private List<Expression> values = new ArrayList<>();


    InsertImpl(Class<?> clazz) {
        super(clazz);
        insert = new net.sf.jsqlparser.statement.insert.Insert();
        insert.setTable(table);
        insert.setColumns(columns);
        insert.setItemsList(new ExpressionList(values));
    }

    @Override
    public Insert field(String... fieldNames) {
        for (String fieldName : fieldNames) {
            columns.add(new Column(getColumn(fieldName)));
        }
        return this;
    }

    @Override
    public Insert values(Object... values) {
        for (Object value : values) {
            this.values.add(PARAM_EXPR);
            params.add(value);
        }
        return this;
    }

    @Override
    public Insert nativeValues(String... values) {
        for (String value : values) {
            this.values.add(new HexValue(value));
        }
        return this;
    }

    @Override
    public Insert initEntity(Object entity) {
        if (entity == null) {
            return this;
        }
        Map<String, String> fieldColumnMap = tableInfo.getFieldColumnMap();

        for (Map.Entry<String, String> entry : fieldColumnMap.entrySet()) {
            Object obj = BeanUtils.invokeReaderMethod(entity, entry.getKey());
            if (obj != null && !(obj instanceof String && ((String) obj).isEmpty())) {
                columns.add(new Column(entry.getValue()));
                values.add(PARAM_EXPR);
                params.add(obj);
            }
        }
        return this;
    }

    @Override
    public Object execute() {

        StatementContext context = new StatementContext();

        KeyGenerator generator = getKeyGenerator();
        Serializable idValue = null;
        if (generator != null) {
            this.field(tableInfo.getPkField());
            if (generator instanceof SequenceGenerator) {
                idValue = ((SequenceGenerator) generator).generateKey(dbName, tableInfo.getSequenceName());
                nativeValues(String.valueOf(idValue));
            } else {
                idValue = generator.generateKey(dbName);
                values(idValue);
            }
        }

        context.setGenerateKeyByDb(tableInfo.isAutoGenerateKey() && (generator == null || generator.isGenerateBySql()));
        context.setPkColumn(tableInfo.getPkColumn());
        context.setSql(insert.toString());
        context.setParams(params);
        context.setStatementType(StatementContext.StatementType.INSERT);

        Object obj = executor.execute(context);

        return context.isGenerateKeyByDb() ? obj : idValue;
    }

    /**
     * 主键生成器实例的缓存
     */
    private static final Map<String, KeyGenerator> KEY_GENERATOR_CACHE = Collections.synchronizedMap(new HashMap<String, KeyGenerator>());

    private static final SequenceGenerator SEQUENCE_GENERATOR = new SequenceGenerator();

    private KeyGenerator getKeyGenerator() {

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

}
