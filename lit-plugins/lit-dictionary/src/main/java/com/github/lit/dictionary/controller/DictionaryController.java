package com.github.lit.dictionary.controller;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.plugin.web.ViewName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/4/8 20:39
 * version $Id: DictionaryController.java, v 0.1 Exp $
 */
@Slf4j
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/dictionary")
public class DictionaryController {

    private static final String REDIRECT_PREFIX = "'" + PluginConst.REDIRECT + "/dictionary/list'";

    @Resource
    private DictionaryService dictionaryService;

    @GetMapping({"/list", "/list/{parentId}"})
    @ViewName("dictionary")
    public List<Dictionary> childList(DictionaryQo qo, @PathVariable(required = false) Long parentId) {
        qo.setParentId(parentId == null ? 0L : parentId);
        qo.setOrder(true);
        return dictionaryService.findPageList(qo);
    }

    @GetMapping("/back/{dictId}")
    @ViewName(REDIRECT_PREFIX + "+(#data == 0L? '' : '/' + #data)")
    public Long back(@PathVariable Long dictId) {
        Dictionary dictionary = dictionaryService.findById(dictId);
        return dictionary == null ? 0L : dictionary.getParentId();
    }

    @GetMapping("/{id}")
    public Dictionary get(@PathVariable Long id) {
        return dictionaryService.findById(id);
    }

    @PostMapping
    @ViewName(REDIRECT_PREFIX + "+(#dictionary.parentId == 0L? '' : '/' + #dictionary.parentId)")
    public Long add(Dictionary dictionary) {
        return dictionaryService.insert(dictionary);
    }

    @PutMapping
    @ViewName(REDIRECT_PREFIX + "+(#dictionary.parentId == 0L? '' : '/' + #dictionary.parentId)")
    public void update(Dictionary dictionary) {
        dictionaryService.update(dictionary);
    }

    @DeleteMapping("/test")
    @ViewName(REDIRECT_PREFIX)
    public void delete(Long[] ids) {
        dictionaryService.deleteByIds(ids);
    }


}
