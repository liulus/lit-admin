package com.lit.dao.generator;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017-3-5 22:46
 * version $Id: SequenceGenerator.java, v 0.1 Exp $
 */
public class SequenceGenerator implements KeyGenerator {


    @Override
    public boolean isGenerateBySql() {
        return true;
    }

    @Override
    public Serializable generateKey() {
        return ".nextval";
    }

    public String generateSeqKey(String seqName) {
        return seqName + ".nextval";
    }
}
