package com.github.lit.dictionary.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.plugin.web.ViewName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String back(@PathVariable Long dictId) {
        Dictionary dictionary = dictionaryService.findById(dictId);
        String suffix = dictionary.getParentId() == 0L ? "" : String.valueOf(dictionary.getParentId());
        return "redirect:/plugin/dictionary/list" + suffix;
    }

    @GetMapping("/{id}")
    public Dictionary get(@PathVariable Long id) {
        return dictionaryService.findById(id);
    }

    @PostMapping
    @ViewName(PluginConst.REDIRECT + "/dictionary/list")
    public Long add(Dictionary dictionary, Model model) {
        model.addAttribute(ResultConst.MASSAGE, "新增字典成功!");
        return dictionaryService.insert(dictionary);
    }

    @PutMapping("/update")
    @ViewName(PluginConst.REDIRECT + "/dictionary/list")
    public void update(Dictionary dictionary) {
        dictionaryService.update(dictionary);
    }

    @DeleteMapping("/delete")
    @ViewName(PluginConst.REDIRECT + "/dictionary/list")
    public void delete(Long... ids) {
        dictionaryService.deleteByIds(ids);
    }


}
