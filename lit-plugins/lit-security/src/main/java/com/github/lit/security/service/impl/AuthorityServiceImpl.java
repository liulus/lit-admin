package com.github.lit.security.service.impl;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.security.context.SecurityConst;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.AuthorityQo;
import com.github.lit.security.model.AuthorityVo;
import com.github.lit.security.model.RoleAuthority;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.support.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private JdbcRepository jdbcRepository;

    @Resource
    private DictionaryService dictionaryService;


    @Override
    public PageResult<Authority> findPageList(AuthorityQo qo) {
        return jdbcRepository.selectPageList(Authority.class, qo);
    }

    @Override
    public List<AuthorityVo> findAuthorityTree() {

        List<Authority> authorities = jdbcRepository.selectAll(Authority.class);

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
        return jdbcRepository.selectAll(Authority.class);
    }


    @Override
    public List<Authority> findByRoleId(Long roleId) {
        List<RoleAuthority> roleAuthorities = jdbcRepository.selectListByProperty(RoleAuthority::getRoleId, roleId);
        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        List<Long> authIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        return jdbcRepository.selectByIds(Authority.class, authIds);
    }

    @Override
    public List<Authority> findByRoleIds(List<Long> roleIds) {

        AuthorityQo authorityQo = new AuthorityQo();
        authorityQo.setUserIds(roleIds);
        List<RoleAuthority> roleAuthorities = jdbcRepository.selectList(RoleAuthority.class, authorityQo);

        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return Collections.emptyList();
        }
        List<Long> authIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        return jdbcRepository.selectByIds(Authority.class, authIds);
    }

    @Override
    public Authority findById(Long id) {
        return jdbcRepository.selectById(Authority.class, id);
    }

    @Override
    public Authority findByCode(String code) {
        return jdbcRepository.selectByProperty(Authority::getCode, code);
    }

    @Override
    public Long insert(Authority authority) {
        checkAuthorityCode(authority.getCode());
        jdbcRepository.insert(authority);
        return authority.getId();
    }

    @Override
    public void update(Authority authority) {
        Authority oldAuthority = findById(authority.getId());
        Optional.ofNullable(oldAuthority)
                .map(Authority::getCode)
                .filter(code -> !Objects.equals(code, authority.getCode()))
                .ifPresent(this::checkAuthorityCode);
        jdbcRepository.updateSelective(authority);
    }

    private void checkAuthorityCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new BizException("权限码不能为空!");
        }
        int count = jdbcRepository.countByProperty(Authority::getCode, code);
        if (count > 0) {
            throw new BizException("权限码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return jdbcRepository.deleteByIds(Authority.class, Arrays.asList(ids));
    }


}
