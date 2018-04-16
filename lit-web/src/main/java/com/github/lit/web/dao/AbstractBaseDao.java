package com.github.lit.web.dao;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.page.Page;
import com.github.lit.commons.util.ClassUtils;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.annotation.Transient;
import com.github.lit.jdbc.statement.select.Select;
import com.google.common.base.Strings;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * User : liulu
 * Date : 2018-03-18 11:41
 * version $Id: AbstractBaseDao.java, v 0.1 Exp $
 */
public abstract class AbstractBaseDao<PO> implements BaseDao<PO> {

    @Resource
    protected JdbcTools jdbcTools;

    private Class<PO> poClass;

    public AbstractBaseDao() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!Object.class.equals(genericSuperclass)) {
            Type poType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            //noinspection unchecked
            poClass = (Class<PO>) poType;
        }
    }

    @Override
    public Long insert(PO po) {
        return jdbcTools.insert(po);
    }

    @Override
    public int update(PO po) {
        return jdbcTools.update(po);
    }

    @Override
    public int delete(PO po) {
        return jdbcTools.delete(po);
    }

    @Override
    public int deleteByIds(Long... ids) {
        return jdbcTools.deleteByIds(poClass, (Serializable[]) ids);
    }

    @Override
    public Select<PO> getSelect() {
        return jdbcTools.select(poClass);
    }

    @Override
    public PO findById(Long id) {
        return jdbcTools.get(poClass, id);
    }

    @Override
    public List<PO> findByIds(Long[] ids) {
        return jdbcTools.findByIds(poClass, ids);
    }

    @Override
    public PO findByProperty(String property, Object value) {
        return getSelect().where(property).equalsTo(value).single();
    }

    @Override
    public <QO> PO findSingle(QO qo) {
        Select<PO> select = getSelect();
        buildCondition(select, qo);
        return select.single();
    }

    @Override
    public <QO extends Page> List<PO> findPageList(QO qo) {
        Select<PO> select = getSelect();
        this.buildCondition(select, qo);
        return select.page(qo).list();
    }

    @Override
    public <QO> List<PO> findList(QO qo) {
        Select<PO> select = getSelect();
        this.buildCondition(select, qo);
        return select.list();
    }

    @Override
    public <QO> int count(QO qo) {
        Select<PO> select = getSelect();
        this.buildCondition(select, qo);
        return select.count();
    }

    protected void buildCondition(Select<PO> select, Object qo) {

        Field[] poFields = poClass.getDeclaredFields();

        for (Field poField : poFields) {
            if (poField.isAnnotationPresent(Transient.class)) {
                continue;
            }
            PropertyDescriptor qoPd = BeanUtils.getPropertyDescriptor(qo.getClass(), poField.getName());
            if (qoPd != null && qoPd.getReadMethod() != null) {
                Object value = ClassUtils.invokeMethod(qoPd.getReadMethod(), qo);
                if (value != null && (!(value instanceof String) || !Strings.isNullOrEmpty((String) value))) {
                    select.and(qoPd.getName()).equalsTo(value);
                }
            }
        }
    }
}
