package net.skeyurt.lit.dao.annotation;

import net.skeyurt.lit.dao.transfer.CriteriaTransfer;

/**
 * User : liulu
 * Date : 2017/4/8 21:32
 * version $Id: TransferClass.java, v 0.1 Exp $
 */
public @interface TransferClass {

    Class<? extends CriteriaTransfer> value();
}
