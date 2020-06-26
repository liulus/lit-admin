package com.github.lit.rest;

import com.github.lit.constant.AuthorityConst;
import com.github.lit.model.DictionaryQo;
import com.github.lit.model.DictionaryVo;
import com.github.lit.service.DictionaryService;
import com.lit.support.data.domain.Page;
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
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;


    @GetMapping("/list")
    @Secured(AuthorityConst.VIEW_DICTIONARY)
    public Page<DictionaryVo.Detail> findAllRootDict(DictionaryQo qo) {
        qo.setParentId(0L);
        return dictionaryService.findPageList(qo);
    }

    @GetMapping("/detail/{id}")
    public DictionaryVo.Detail findAllById(@PathVariable Long id) {
        return dictionaryService.buildDictLevelById(id);
    }

    @PostMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public Long add(@RequestBody DictionaryVo.Add dictionary) {
        return dictionaryService.insert(dictionary);
    }

    @PutMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public int update(@RequestBody DictionaryVo.Update dictionary) {
        return dictionaryService.update(dictionary);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dictionaryService.deleteByIds(id);
    }




}