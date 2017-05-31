package net.skeyurt.lit.dictionary.qo.qct;

import net.skeyurt.lit.dao.builder.SqlSelect;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.transfer.QueryTransfer;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017/4/8 21:47
 * version $Id: DictionaryTransfer.java, v 0.1 Exp $
 */
public class DictionaryTransfer implements QueryTransfer<DictionaryQo> {

    @Override
    public void transQuery(DictionaryQo qo, SqlSelect select, Class<?> entityClass) {

        if (StringUtils.isNotEmpty(qo.getKeyWord())) {
            select.andWithBracket("dictKey", Operator.LIKE, qo.getBlurKeyWord());
            select.or("dictValue", Operator.LIKE, qo.getBlurKeyWord());
            select.or("memo", Operator.LIKE, qo.getBlurKeyWord()).end();
        }

        if (StringUtils.isNotBlank(qo.getDictKey())) {
            select.and("dictKey",  qo.getDictKey());
        }
        if (StringUtils.isNotBlank(qo.getDictValue())) {
            select.and("dictValue", Operator.LIKE, "%" + qo.getDictValue() + "%");
        }
        if (StringUtils.isNotBlank(qo.getMemo())) {
            select.and("memo", Operator.LIKE, "%" + qo.getMemo() + "%");
        }
        if (qo.getDictLevel() != null) {
            select.and("dictLevel", qo.getDictLevel());
        } else {
            select.and("parentId", qo.getParentId());
        }

        select.asc("orderNum");
    }
}
