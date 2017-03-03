package com.lit.dao.transfer;

import com.lit.commons.bean.BeanUtils;
import com.lit.commons.utils.ClassUtils;
import com.lit.dao.annotation.Transient;
import com.lit.dao.builder.Criteria;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;

/**
 * User : liulu
 * Date : 2017-2-18 21:16
 * version $Id: DefaultCriteriaTransfer.java, v 0.1 Exp $
 */
public class DefaultCriteriaTransfer<Qo> implements CriteriaTransfer<Qo> {

    @Override
    public void transQuery(Qo qo, Criteria criteria, Class<?> entityClass) {
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(qo.getClass());

        for (PropertyDescriptor pd : pds) {
            PropertyDescriptor entityPd = BeanUtils.getPropertyDescriptor(entityClass, pd.getName());
            if (entityPd != null && !"class".equals(pd.getName()) && pd.getReadMethod() != null) {
                try {
                    if (entityClass.getDeclaredField(pd.getName()).isAnnotationPresent(Transient.class)) {
                        continue;
                    }
                } catch (NoSuchFieldException e) {
                    continue;
                }
                Object value = ClassUtils.invokeMethod(pd.getReadMethod(), qo);
                if (value != null && (!(value instanceof String) || StringUtils.isNotBlank((String) value))) {
                    criteria.and(pd.getName(), value);
                }
            }
        }
    }
}
