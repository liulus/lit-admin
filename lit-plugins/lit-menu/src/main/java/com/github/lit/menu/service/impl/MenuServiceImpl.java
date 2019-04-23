package com.github.lit.menu.service.impl;

import com.github.lit.menu.event.MenuUpdateEvent;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.plugin.core.model.LoginUser;
import com.github.lit.plugin.core.util.PluginUtils;
import com.github.lit.support.event.Event;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.support.sql.SQL;
import com.github.lit.support.sql.TableMetaDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private JdbcRepository jdbcRepository;

    @Override
    public PageResult<Menu> findPageList(MenuQo qo) {
        return jdbcRepository.selectPageList(Menu.class, qo);
    }

    @Override
    public Menu findById(Long id) {
        return jdbcRepository.selectById(Menu.class, id);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public Long insert(Menu menu) {

        checkMenuCode(menu.getCode(), menu.getParentId());
        menu.setEnable(true);

        jdbcRepository.insert(menu);
        return menu.getId();
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public int update(Menu menu) {

        Menu old = findById(menu.getId());
        if (old == null) {
            return 0;
        }
        if (!Objects.equals(menu.getCode(), old.getCode())) {
            checkMenuCode(menu.getCode(), menu.getParentId());
        }

        return jdbcRepository.updateSelective(menu);
    }

    private void checkMenuCode(String code, Long parentId) {
        Menu menu = findByCodeAndParentId(code, parentId);
        if (menu != null) {
            throw new BizException("菜单编码已经存在");
        }
    }

    private Menu findByCodeAndParentId(String code, Long parentId) {
        TableMetaDate metaDate = TableMetaDate.forClass(Menu.class);
        SQL sql = SQL.init().SELECT(metaDate.getAllColumns())
                .FROM(metaDate.getTableName())
                .WHERE("parent_id = :parentId")
                .WHERE("code = :code");

        Map<String, Object> params = new HashMap<>(2);
        params.put("parentId", parentId == null ? 0L : parentId);
        params.put("code", code);

        return jdbcRepository.selectForObject(sql, params, Menu.class);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        List<Menu> menus = jdbcRepository.selectByIds(Menu.class, Arrays.asList(ids));
        List<Long> validIds = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            int count = countByParentId(menu.getId());
            if (count > 0) {
                throw new BizException(String.format("请先删除 %s 的子菜单数据 !", menu.getName()));
            }
            validIds.add(menu.getId());
        }
        return jdbcRepository.deleteByIds(Menu.class, validIds);
    }

    private int countByParentId(Long parentId) {
        TableMetaDate metaDate = TableMetaDate.forClass(Menu.class);
        SQL sql = SQL.init().SELECT("count(*)")
                .FROM(metaDate.getTableName())
                .WHERE("parent_id = :parentId");
        Map<String, Long> params = Collections.singletonMap("parentId", parentId == null ? 0L : parentId);
        return jdbcRepository.selectForObject(sql, params, int.class);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public void moveMenu(Long parentId, Long[] ids) {

        Arrays.sort(ids);
        // 验证新的 parentId 不是 被移动菜单本身
        if (parentId != null && Arrays.binarySearch(ids, parentId) >= 0) {
            throw new BizException("父菜单不能是自己 !");
        }

        // 验证新的 parentId 不是 被移动菜单的子菜单
        Menu menu = findById(parentId);
        while (menu != null) {
            if (menu.getParentId() != null && Arrays.binarySearch(ids, menu.getParentId()) >= 0) {
                throw new BizException("无法移动到子菜单下 !");
            }
            menu = findById(menu.getParentId());
        }

//        menuDao.move(parentId, ids);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public void changeStatus(Long id, boolean isEnable) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setEnable(isEnable);
        jdbcRepository.updateSelective(menu);
    }

    @Override
    public List<Menu> findAll() {
        return jdbcRepository.selectAll(Menu.class);
    }

    @Override
    public List<Menu> findByAuthorities(List<String> authorities) {
        TableMetaDate metaDate = TableMetaDate.forClass(Menu.class);
        SQL sql = SQL.init().SELECT(metaDate.getAllColumns())
                .FROM(metaDate.getTableName())
                .WHERE("auth_code = '' or auth_code in (:authorities)");

        return jdbcRepository.selectForList(sql, Collections.singletonMap("authorities", authorities), Menu.class);
    }

    @Override
    public List<Menu> findMyMenus() {
        LoginUser loginUser = PluginUtils.getLoginUser();
        return findByAuthorities(loginUser.getAuths());
    }

}
