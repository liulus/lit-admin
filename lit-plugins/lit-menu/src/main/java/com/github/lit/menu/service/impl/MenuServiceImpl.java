package com.github.lit.menu.service.impl;

import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.support.data.SQL;
import com.github.lit.support.data.domain.Page;
import com.github.lit.support.data.jdbc.JdbcRepository;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.util.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private static final String PARENT_ID_CONDITION = "parent_id = :parentId";

    @Resource
    private JdbcRepository jdbcRepository;

    @Override
    public Page<Menu> findPageList(MenuQo qo) {
        SQL sql = SQL.baseSelect(com.github.lit.dictionary.model.Dictionary.class);
        if (qo.getParentId() != null) {
            sql.WHERE(PARENT_ID_CONDITION);
        }
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(code like :keyword or name like :keyword or remark like :keyword)");
        }
        sql.ORDER_BY("order_num");
        return jdbcRepository.selectForPageList(sql, qo, Menu.class);
    }

    @Override
    public Menu findById(Long id) {
        return jdbcRepository.selectById(Menu.class, id);
    }

    @Override
    public List<MenuVo.Detail> buildMenuTree(boolean filterDisabled, boolean filterEmpty) {
        SQL sql = SQL.baseSelect(Menu.class);
        if (filterDisabled) {
            sql.WHERE("is_enable = 1");
        }
        List<Menu> menus = jdbcRepository.selectForList(sql, null, Menu.class);

        Map<Long, List<MenuVo.Detail>> menuMap = menus.stream()
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Detail()))
                .collect(Collectors.groupingBy(MenuVo.Detail::getParentId));
        List<MenuVo.Detail> rootMenus = menuMap.get(0L);

        setChildren(rootMenus, menuMap);

        if(filterEmpty) {
            return rootMenus.stream()
                    .filter(detail -> StringUtils.hasText(detail.getUrl()) || detail.getIsParent())
                    .collect(Collectors.toList());
        }
        return rootMenus;
    }

    private void setChildren(List<MenuVo.Detail> parents, Map<Long, List<MenuVo.Detail>> nodeMap) {
        if (CollectionUtils.isEmpty(parents)) {
            return;
        }
        for (MenuVo.Detail menu : parents) {
            List<MenuVo.Detail> children = nodeMap.get(menu.getId());
            if (CollectionUtils.isEmpty(children)) {
                menu.setIsParent(false);
            } else {
                menu.setIsParent(true);
                menu.setChildren(children);
                setChildren(children, nodeMap);
            }
        }
    }


    @Override
    public Long insert(Menu menu) {

        checkMenuCode(menu.getCode(), menu.getParentId());
        menu.setEnable(true);

        jdbcRepository.insert(menu);
        return menu.getId();
    }

    @Override
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
        SQL sql = SQL.baseSelect(Menu.class)
                .WHERE(PARENT_ID_CONDITION)
                .WHERE("code = :code");

        Map<String, Object> params = new HashMap<>(2);
        params.put("parentId", parentId == null ? 0L : parentId);
        params.put("code", code);

        return jdbcRepository.selectForObject(sql, params, Menu.class);
    }

    @Override
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
        return jdbcRepository.countByProperty(Menu::getParentId, parentId == null ? 0L : parentId);
    }

    @Override
    public void changeStatus(Long id) {
        Menu menu = findById(id);
        if (menu == null) {
            return;
        }
        Menu upMenu = new Menu();
        upMenu.setId(id);
        upMenu.setEnable(!menu.getEnable());
        jdbcRepository.updateSelective(upMenu);
    }

    @Override
    public List<Menu> findAll() {
        return jdbcRepository.selectAll(Menu.class);
    }

}
