package net.skeyurt.lit.dao.generator;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017-3-5 22:41
 * version $Id: KeyGenerator.java, v 0.1 Exp $
 */
public interface KeyGenerator {


    boolean isGenerateBySql();

    Serializable generateKey(String dbName);

}
