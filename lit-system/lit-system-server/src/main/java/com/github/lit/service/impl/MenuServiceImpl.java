package com.github.lit.service.impl;

import com.github.lit.model.MenuQo;
import com.github.lit.model.MenuVo;
import com.github.lit.repository.MenuRepository;
import com.github.lit.repository.entity.Menu;
import com.github.lit.service.MenuService;
import com.lit.support.data.domain.Page;
import com.lit.support.exception.BizException;
import com.lit.support.util.bean.BeanUtils;
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
    private MenuRepository menuRepository;

    @Override
    public Page<Menu> findPageList(MenuQo qo) {
        return menuRepository.selectPageList(qo);
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.selectById(id);
    }

    @Override
    public List<MenuVo.Detail> buildMenuTree(boolean filterDisabled, boolean filterEmpty) {
        Menu params = new Menu();
        if (filterDisabled) {
            params.setEnable(Boolean.TRUE);
        }
        List<Menu> menus = menuRepository.selectList(params);

        Map<Long, List<MenuVo.Detail>> menuMap = menus.stream()
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Detail()))
                .collect(Collectors.groupingBy(MenuVo.Detail::getParentId));
        List<MenuVo.Detail> rootMenus = menuMap.get(0L);

        setChildren(rootMenus, menuMap);

        if (filterEmpty) {
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

        menuRepository.insert(menu);
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

        return menuRepository.updateSelective(menu);
    }

    private void checkMenuCode(String code, Long parentId) {
        Menu menu = menuRepository.findByCodeAndParentId(code, parentId);
        if (menu != null) {
            throw new BizException("菜单编码已经存在");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        List<Menu> menus = menuRepository.selectByIds(Arrays.asList(ids));
        List<Long> validIds = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            int count = countByParentId(menu.getId());
            if (count > 0) {
                throw new BizException(String.format("请先删除 %s 的子菜单数据 !", menu.getName()));
            }
            validIds.add(menu.getId());
        }
        return menuRepository.deleteByIds(validIds);
    }

    private int countByParentId(Long parentId) {
        return menuRepository.countByProperty(Menu::getParentId, parentId == null ? 0L : parentId);
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
        menuRepository.updateSelective(upMenu);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.selectAll();
    }

}
