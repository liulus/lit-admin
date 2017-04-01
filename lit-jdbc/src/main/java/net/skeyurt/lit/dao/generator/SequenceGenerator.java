package net.skeyurt.lit.dao.generator;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017-3-5 22:46
 * version $Id: SequenceGenerator.java, v 0.1 Exp $
 */
public class SequenceGenerator implements KeyGenerator {

    private static final ThreadLocal<String> SEQUENCE_NAME = new ThreadLocal<>();

    @Override
    public boolean isGenerateBySql() {
        return true;
    }

    @Override
    public Serializable generateKey(String dbName) {
        String seqName = getSequenceName();
        removeSequenceName();
        switch (dbName) {
            case "DB2":
                return "next value for " + seqName;
            case "ORACLE":
                return seqName + ".nextval";
            default:
                return "";
        }
    }

    public static void setSequenceName(String sequenceName) {
        SEQUENCE_NAME.set(sequenceName);
    }

    public static String getSequenceName() {
        return SEQUENCE_NAME.get();
    }

    public static void removeSequenceName() {
        SEQUENCE_NAME.remove();
    }

}
