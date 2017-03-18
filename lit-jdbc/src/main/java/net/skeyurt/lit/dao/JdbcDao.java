package net.skeyurt.lit.dao;

import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dao.transfer.CriteriaTransfer;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017-1-2 21:16
 * version $Id: JdbcDao.java, v 0.1 Exp $
 */
public interface JdbcDao {

    /**
     * 插入一条记录，参数可以是实体，也可以是 Criteria 实例
     *
     * @param t    实体 或  Criteria 实例
     * @param <T>  实体 或  Criteria
     * @return 插入记录的 Id
     */
    <T, ID extends Serializable> ID insert(T t);

    /**
     * 删除一条记录，参数可以是实体，也可以是 Criteria 实例
     * 若参数是实体，是根据实体的 Id 删除记录，须保证实体的 Id 不为空，
     *
     * @param t   实体 或  Criteria 实例
     * @param <T> 实体 或  Criteria
     * @return 数据库实际受影响的记录数
     */
    <T> int delete(T t);

    /**
     * 根据 Id 删除多条记录
     *
     * @param clazz 删除表对应的实体 Class
     * @param ids   要删除记录的 ids
     * @param <T>   删除表对应的实体
     * @return 数据库实际受影响的记录数
     */
    <T> int deleteByIds(Class<T> clazz, Serializable... ids);

    /**
     * 更新一条记录，参数可以是实体，也可以是 Criteria 实例
     * 若参数是实体，是根据实体的 Id 更新记录，须保证实体的 Id 不为空，会忽略实体中的空属性（实体中值为空的属性不会更新到数据库）
     *
     * @param t   实体 或  Criteria 实例
     * @param <T> 实体 或  Criteria
     * @return 数据库实际受影响的记录数
     */
    <T> int update(T t);

    /**
     * 更新一条记录，参数可以是实体，也可以是 Criteria 实例
     * 若参数是实体，是根据实体的 Id 更新记录，须保证实体的 Id 不为空
     *
     * @param t            实体 或  Criteria 实例
     * @param isIgnoreNull 是否忽略实体中的空属性（true: 不更新空值属性；false: 更新空值属性）
     * @param <T>          实体 或  Criteria
     * @return 数据库实际受影响的记录数
     */
    <T> int update(T t, boolean isIgnoreNull);

    /**
     * 根据 Id 查询一条记录
     *
     * @param clazz 查询记录对应的实体 Class
     * @param id    查询记录的 Id
     * @param <T>   查询记录对应的实体
     * @return 查询记录对应的实体
     */
    <T> T get(Class<T> clazz, Serializable id);

    /**
     * 根据属性查询一条记录
     * @param clazz 查询记录对应的实体 Class
     * @param propertyName 属性的名称
     * @param propertyValue 对应的值
     * @param <T> 实体类型
     * @return 实体对象
     */
    <T> T findByProperty (Class<T> clazz, String propertyName, Object propertyValue);

    /**
     * 根据查询对象 qo 查询一条记录
     *
     * @param clazz 查询记录对应的实体 Class
     * @param qo    查询对象
     * @param <T>   查询记录对应的实体
     * @return 查询记录对应的实体
     */
    <T, Qo> T queryForSingle(Class<T> clazz, Qo qo);

    /**
     * 根据 criteria 查询一条记录
     *
     * @param criteria Criteria 实例
     * @param <T>      查询记录对应的实体
     * @return 查询记录对应的实体
     */
    <T> T queryForSingle(Criteria<T> criteria);

    /**
     * 根据 sql 查询一条记录
     *
     * @param clazz 查询记录对应的实体 Class
     * @param sql   sql 语句
     * @param args  sql 语句对应的参数
     * @param <T>   查询记录对应的实体
     * @return 查询记录对应的实体
     */
    <T> T queryForSingle(Class<T> clazz, String sql, Object... args);

    /**
     * 根据 sql 查询一条记录
     *
     * @param sql  sql 语句
     * @param args sql 语句对应的参数
     * @return 表字段名为 key，字段值为 value 的 Map
     */
    Map<String, Object> queryForSingle(String sql, Object... args);

    /**
     * 根据查询对象 qo 查询列表
     *
     * @param clazz 查询记录对应的实体 Class
     * @param qo    查询对象
     * @param <T>   查询记录对应的实体
     * @return 查询记录对应的实体列表
     */
    <T, Qo> List<T> query(Class<T> clazz, Qo qo);

    /**
     * 查询列表
     *
     * @param clazz
     * @param qo       查询对象
     * @param transfer 查询对象转 Criteria
     * @param <T>
     * @param <Qo>
     * @return
     */
    <T, Qo> List<T> query(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer);

    /**
     * 根据 criteria 查询列表
     *
     * @param criteria Criteria 实例
     * @param <T>      查询记录对应的实体
     * @return 查询记录对应的实体列表
     */
    <T> List<T> query(Criteria<T> criteria);

    /**
     * 根据 sql 查询列表
     *
     * @param clazz 查询记录对应的实体 Class
     * @param sql   sql 语句
     * @param args  sql 语句对应的参数
     * @param <T>   查询记录对应的实体
     * @return 查询记录对应的实体列表
     */
    <T> List<T> query(Class<T> clazz, String sql, Object... args);

    /**
     * 根据 qo 查询带分页信息的列表
     *
     * @param clazz
     * @param qo
     * @param <T>
     * @return
     */
    <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo);

    /**
     * 根据 qo 查询带分页信息的列表
     *
     * @param clazz
     * @param qo
     * @param transfer
     * @param <T>
     * @param <Qo>
     * @return
     */
    <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer);

    /**
     * 根据 sql 查询列表
     *
     * @param sql  sql 语句
     * @param args sql 语句对应的参数
     * @return 表字段名为 key，字段值为 value 的 Map 列表
     */
    List<Map<String, Object>> query(String sql, Object... args);

    /**
     * 根据查询对象查询总记录数
     *
     * @param clazz 查询记录对应的实体 Class
     * @param qo    查询对象
     * @param <T>   查询记录对应的实体
     * @return 总记录数
     */
    <T, Qo> int count(Class<T> clazz, Qo qo);

    /**
     * 查询总记录数
     *
     * @param clazz    查询记录对应的实体 Class
     * @param qo       查询对象
     * @param transfer
     * @param <T>
     * @param <Qo>
     * @return
     */
    <T, Qo> int count(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer);

    /**
     * 根据 criteria 查询总记录数
     *
     * @param criteria Criteria
     * @return 总记录数
     */
    int count(Criteria criteria);

    /**
     * 根据 sql 查询总记录数
     *
     * @param sql  sql 语句
     * @param args sql 语句对应的参数
     * @return 总记录数
     */

    int count(String sql, Object... args);


    /**
     * @return JdbcOperations
     */
    JdbcOperations getJdbcTemplate();
}
