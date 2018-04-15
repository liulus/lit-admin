package com.github.lit.security.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.exception.BizException;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.tool.DictionaryTools;
import com.github.lit.security.context.SecurityConst;
import com.github.lit.security.dao.AuthorityDao;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.AuthorityQo;
import com.github.lit.security.model.AuthorityVo;
import com.github.lit.security.service.AuthorityService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityDao authorityDao;


    @Override
    public List<Authority> findPageList(AuthorityQo qo) {

        return authorityDao.findPageList(qo);
    }

    @Override
    public List<AuthorityVo> findAuthorityTree() {

        List<Authority> authorities = authorityDao.getSelect().list();

        List<Dictionary> authorityTypes = DictionaryTools.findChildByRootKey(SecurityConst.AUTHORITY_TYPE);
        if (CollectionUtils.isEmpty(authorityTypes)) {
            return BeanUtils.convert(AuthorityVo.class, authorities);
        }

        List<AuthorityVo> result = new ArrayList<>();

        Map<String, List<AuthorityVo>> typeMap = authorities.stream()
                .filter(authority -> !Strings.isNullOrEmpty(authority.getModule()))
                .map(authority -> BeanUtils.convert(authority, new AuthorityVo()))
                .collect(Collectors.groupingBy(Authority::getModule));

        authorityTypes.forEach(dictionary -> {
            List<AuthorityVo> childVos = typeMap.get(dictionary.getDictKey());
            if (!CollectionUtils.isEmpty(childVos)) {
                AuthorityVo authorityVo = new AuthorityVo();
                authorityVo.setIsParent(true);
                authorityVo.setNocheck(true);
                authorityVo.setCode(dictionary.getDictKey());
                authorityVo.setName(dictionary.getDictValue());
                authorityVo.setChildren(childVos);
                result.add(authorityVo);
            }
        });

        // 过滤没有权限类型的
        List<AuthorityVo> other = authorities.stream()
                .filter(authority -> Strings.isNullOrEmpty(authority.getModule()))
                .map(authority -> BeanUtils.convert(authority, new AuthorityVo()))
                .collect(Collectors.toList());
        result.addAll(other);
        return result;
    }

    @Override
    public List<Authority> findAll() {
        return authorityDao.getSelect().list();
    }


    @Override
    public List<Authority> findByRoleId(Long roleId) {
        return authorityDao.findByRoleIds(roleId);
    }

    @Override
    public List<Authority> findByRoleIds(Long[] roleIds) {
        return authorityDao.findByRoleIds(roleIds);
    }

    @Override
    public Authority findById(Long id) {
        return authorityDao.findById(id);
    }

    @Override
    public Authority findByCode(String code) {
        return authorityDao.findByProperty("code", code);
    }

    @Override
    public Long insert(Authority authority) {
        checkAuthorityCode(authority.getCode());
        return authorityDao.insert(authority);
    }

    @Override
    public void update(Authority authority) {
        Authority oldAuthority = findById(authority.getId());
        Optional.ofNullable(oldAuthority)
                .map(Authority::getCode)
                .filter(code -> !Objects.equals(code, authority.getCode()))
                .ifPresent(this::checkAuthorityCode);
        authorityDao.update(authority);
    }

    private void checkAuthorityCode(String code) {
        if (Strings.isNullOrEmpty(code)) {
            throw new BizException("权限码不能为空!");
        }
        int count = authorityDao.getSelect().where(Authority::getCode).equalsTo(code).count();
        if (count > 0) {
            throw new BizException("权限码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        return authorityDao.deleteByIds(ids);
    }


}
