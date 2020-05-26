package com.lit.service.security.service.impl;

import com.lit.service.dictionary.model.Dictionary;
import com.lit.service.dictionary.service.DictionaryService;
import com.lit.service.security.context.SecurityConst;
import com.lit.service.security.model.Authority;
import com.lit.service.security.model.AuthorityQo;
import com.lit.service.security.model.AuthorityVo;
import com.lit.service.security.model.RoleAuthority;
import com.lit.service.security.repository.AuthorityRepository;
import com.lit.service.security.repository.RoleAuthorityRepository;
import com.lit.service.security.service.AuthorityService;
import com.lit.support.data.domain.Page;
import com.lit.support.exception.BizException;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private AuthorityRepository authorityRepository;
    @Resource
    private RoleAuthorityRepository roleAuthorityRepository;

    @Resource
    private DictionaryService dictionaryService;


    @Override
    public Page<Authority> findPageList(AuthorityQo qo) {
        return authorityRepository.selectPageList(qo);
    }

    @Override
    public List<AuthorityVo> findAuthorityTree() {

        List<Authority> authorities = authorityRepository.selectAll();

        List<Dictionary> authorityTypes = dictionaryService.findChildByRootKey(SecurityConst.AUTHORITY_TYPE);
        if (CollectionUtils.isEmpty(authorityTypes)) {
            return BeanUtils.convertList(AuthorityVo.class, authorities);
        }

        List<AuthorityVo> result = new ArrayList<>();

        Map<String, List<AuthorityVo>> typeMap = authorities.stream()
                .filter(authority -> StringUtils.hasText(authority.getModule()))
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
                .filter(authority -> StringUtils.isEmpty(authority.getModule()))
                .map(authority -> BeanUtils.convert(authority, new AuthorityVo()))
                .collect(Collectors.toList());
        result.addAll(other);
        return result;
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.selectAll();
    }


    @Override
    public List<Authority> findByRoleId(Long roleId) {
        List<RoleAuthority> roleAuthorities = roleAuthorityRepository
                .selectListByProperty(RoleAuthority::getRoleId, roleId);
        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        List<Long> authIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        return authorityRepository.selectByIds(authIds);
    }

    @Override
    public List<Authority> findByRoleIds(List<Long> roleIds) {

        List<RoleAuthority> roleAuthorities = roleAuthorityRepository.selectListByProperty(RoleAuthority::getRoleId, roleIds);

        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        List<Long> authIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        return authorityRepository.selectByIds(authIds);
    }

    @Override
    public Authority findById(Long id) {
        return authorityRepository.selectById(id);
    }

    @Override
    public Authority findByCode(String code) {
        return authorityRepository.selectByProperty(Authority::getCode, code);
    }

    @Override
    public Long insert(Authority authority) {
        checkAuthorityCode(authority.getCode());
        authorityRepository.insert(authority);
        return authority.getId();
    }

    @Override
    public void update(Authority authority) {
        Authority oldAuthority = findById(authority.getId());
        Optional.ofNullable(oldAuthority)
                .map(Authority::getCode)
                .filter(code -> !Objects.equals(code, authority.getCode()))
                .ifPresent(this::checkAuthorityCode);
        authorityRepository.updateSelective(authority);
    }

    private void checkAuthorityCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new BizException("权限码不能为空!");
        }
        int count = authorityRepository.countByProperty(Authority::getCode, code);
        if (count > 0) {
            throw new BizException("权限码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return authorityRepository.deleteByIds(Arrays.asList(ids));
    }


}
