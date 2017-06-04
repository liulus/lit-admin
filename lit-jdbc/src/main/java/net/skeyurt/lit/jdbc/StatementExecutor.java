package net.skeyurt.lit.jdbc;

import net.skeyurt.lit.jdbc.model.StatementContext;

/**
 * User : liulu
 * Date : 2017/6/4 9:57
 * version $Id: StatementExecutor.java, v 0.1 Exp $
 */
public interface StatementExecutor {


    Object execute(StatementContext context);


}
