package net.skeyurt.lit.dao.transfer;

import net.skeyurt.lit.commons.util.ClassUtils;
import net.skeyurt.lit.dao.annotation.TransferClass;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * User : liulu
 * Date : 2017/4/8 21:23
 * version $Id: CriteriaTransferFactory.java, v 0.1 Exp $
 */
public class CriteriaTransferFactory {

    private CriteriaTransferFactory() {
    }

    private static final QueryTransfer DEFAULT_TRANSFER = new DefaultQueryTransfer();

    private static final Map<Class<?>, QueryTransfer> TRANSFER_MAP = Collections.synchronizedMap(new WeakHashMap<Class<?>, QueryTransfer>());


    public static <T> QueryTransfer<T> createTransfer(T qo) {
        Class<?> qoClass = qo.getClass();
        TransferClass transferClass = qoClass.getAnnotation(TransferClass.class);
        if (transferClass == null) {
            return DEFAULT_TRANSFER;
        }

        QueryTransfer<T> transfer = TRANSFER_MAP.get(qoClass);
        if (transfer == null) {
            Class<? extends QueryTransfer> clz = transferClass.value();
            transfer = ClassUtils.newInstance(clz);
            TRANSFER_MAP.put(qoClass, transfer);
        }
        return transfer;
    }


}
