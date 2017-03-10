package com.lit.dao.generator;

import org.apache.commons.lang3.StringUtils;

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
    public Serializable generateKey(String dbName) {
        return ".nextval";
    }

    public String generateSeqKey(String dbName, String seqName) {
        seqName = StringUtils.trimToEmpty(seqName);
        switch (dbName) {
            case "DB2":
                return "next value for " + seqName;
            case "ORACLE":
                return seqName + ".nextval";
            default:
                return "";
        }
    }
}
