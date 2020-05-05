package com.lit.service.dictionary.controller;

import com.lit.service.core.constant.AuthorityConst;
import com.lit.service.dictionary.model.Dictionary;
import com.lit.service.dictionary.model.DictionaryQo;
import com.lit.service.dictionary.model.DictionaryVo;
import com.lit.service.dictionary.service.DictionaryService;
import com.lit.support.data.domain.Page;
import com.lit.support.data.domain.PageUtils;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-03-01 15:52
 */
@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;


    @GetMapping("/root/list")
    @Secured(AuthorityConst.VIEW_DICTIONARY)
    public Page<DictionaryVo.Detail> findAllRootDict(DictionaryQo qo) {
        qo.setParentId(0L);
        Page<Dictionary> pageList = dictionaryService.findPageList(qo);
        return PageUtils.convert(pageList, DictionaryVo.Detail.class);
    }

    @GetMapping("/detail/{id}")
    public DictionaryVo.Detail findAllById(@PathVariable Long id) {
        return dictionaryService.buildDictLevelById(id);
    }

    @PostMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public Long add(@RequestBody DictionaryVo.Add dictionary) {
        return dictionaryService.insert(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @PutMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public int update(@RequestBody DictionaryVo.Update dictionary) {
        return dictionaryService.update(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dictionaryService.deleteByIds(id);
    }




}