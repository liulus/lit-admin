package net.skeyurt.lit.dao.generator;

import net.skeyurt.lit.commons.util.UUIDUtils;

/**
 * User : liulu
 * Date : 2017-3-6 20:36
 * version $Id: UUIDGenerator.java, v 0.1 Exp $
 */
public class UUIDGenerator implements KeyGenerator {
    @Override
    public boolean isGenerateBySql() {
        return false;
    }

    @Override
    public String generateKey() {
        return UUIDUtils.getUUID32();
    }
}
