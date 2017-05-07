package net.skeyurt.lit.dictionary.qo;

import lombok.Data;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.dao.annotation.TransferClass;
import net.skeyurt.lit.dictionary.qo.qct.DictionaryTransfer;

/**
 * User : liulu
 * Date : 2017/4/8 20:52
 * version $Id: DictionaryQo.java, v 0.1 Exp $
 */
@Data
@TransferClass(DictionaryTransfer.class)
public class DictionaryQo extends Pager {

    private static final long serialVersionUID = -2144418218430870185L;

    private String dictKey;

    private String dictValue;

    private String memo;

    private Integer dictLevel;

    private Boolean queryRoot;
}
