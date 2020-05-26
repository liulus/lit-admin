package com.lit.service.menu.repository;

import com.lit.service.menu.model.Menu;
import com.lit.support.data.jdbc.JdbcRepository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface MenuRepository extends JdbcRepository<Menu> {

    Menu findByCodeAndParentId(String code, Long parentId);

}
