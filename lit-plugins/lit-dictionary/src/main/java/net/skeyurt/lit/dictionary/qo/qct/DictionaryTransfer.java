package net.skeyurt.lit.dictionary.qo.qct;

import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.transfer.CriteriaTransfer;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017/4/8 21:47
 * version $Id: DictionaryTransfer.java, v 0.1 Exp $
 */
public class DictionaryTransfer implements CriteriaTransfer<DictionaryQo> {

    @Override
    public void transQuery(DictionaryQo qo, Criteria criteria, Class<?> entityClass) {
        if (StringUtils.isNotBlank(qo.getDictKey())) {
            criteria.and("dictKey", Operator.LIKE, "%" + qo.getDictKey() + "%");
        }
        if (StringUtils.isNotBlank(qo.getDictValue())) {
            criteria.and("dictValue", Operator.LIKE, "%" + qo.getDictValue() + "%");
        }
        if (StringUtils.isNotBlank(qo.getMemo())) {
            criteria.and("memo", Operator.LIKE, "%" + qo.getMemo() + "%");
        }
        if (qo.getDictLevel() != null) {
            criteria.and("dictLevel", qo.getDictLevel());
        }
        criteria.asc("orderNum");
    }
}
