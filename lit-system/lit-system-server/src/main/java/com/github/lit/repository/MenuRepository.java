package com.github.lit.repository;

import com.github.lit.repository.entity.Menu;
import com.lit.support.data.jdbc.JdbcRepository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface MenuRepository extends JdbcRepository<Menu> {

    Menu findByCodeAndParentId(String code, Long parentId);

}
