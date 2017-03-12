package net.skeyurt.lit.dao.generator;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017-3-5 23:04
 * version $Id: EmptyKeyGenerator.java, v 0.1 Exp $
 */
public class EmptyKeyGenerator implements KeyGenerator {
    @Override
    public boolean isGenerateBySql() {
        return false;
    }

    @Override
    public Serializable generateKey() {
        return null;
    }
}
