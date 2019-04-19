package com.github.lit.dictionary.controller;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.model.DictionaryVo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.core.constant.AuthorityConst;
import com.github.lit.support.page.Page;
import com.github.lit.support.page.PageUtils;
import com.github.lit.support.util.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-03-01 15:52
 */
@RestController
@RequestMapping("/api/dictionary")
public class DictionaryRestController {

    @Resource
    private DictionaryService dictionaryService;


    @GetMapping("/root/list")
    @Secured(AuthorityConst.VIEW_DICTIONARY)
    public Page<DictionaryVo.Detail> findAllRootDict(DictionaryQo qo) {
        Page<Dictionary> pageList = dictionaryService.findPageList(qo);
        return PageUtils.convert(pageList, DictionaryVo.Detail.class);
    }

    @PostMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public Long addDictionary(@RequestBody DictionaryVo.Add dictionary) {
        return dictionaryService.insert(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @PutMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public int addDictionary(@RequestBody DictionaryVo.Update dictionary) {
        return dictionaryService.update(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @DeleteMapping("/{id}")
    public void deleteDictionary(@PathVariable Long id) {
        dictionaryService.deleteByIds(id);
    }




}