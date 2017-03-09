package com.lit.dao.transfer;

import com.lit.commons.bean.BeanUtils;
import com.lit.dao.annotation.Transient;
import com.lit.dao.builder.Criteria;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * User : liulu
 * Date : 2017-2-18 21:16
 * version $Id: DefaultCriteriaTransfer.java, v 0.1 Exp $
 */
public class DefaultCriteriaTransfer implements CriteriaTransfer {

    @Override
    public void transQuery(Object qo, Criteria criteria, Class entityClass) {

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            Object value = BeanUtils.invokeReaderMethod(qo, field.getName());
            if (value != null && (!(value instanceof String) || StringUtils.isNotBlank((String) value))) {
                criteria.and(field.getName(), value);
            }
        }
    }
}
