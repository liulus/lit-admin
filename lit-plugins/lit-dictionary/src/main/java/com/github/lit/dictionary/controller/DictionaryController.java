package com.github.lit.dictionary.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.context.PluginConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/4/8 20:39
 * version $Id: DictionaryController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/dictionary")
@Slf4j
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping({"/list", ""})
    public String list(DictionaryQo vo, Model model) {
        List<Dictionary> dictionaries = dictionaryService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, dictionaries);

        return "dictionary";
    }

    @RequestMapping("/{parentId}")
    public String childList(DictionaryQo vo, @PathVariable Long parentId, Model model) {

        vo.setParentId(parentId);
        List<Dictionary> dictionaries = dictionaryService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, dictionaries);
        return "dictionary";
    }

    @RequestMapping("/back/{dictId}")
    public String back(@PathVariable Long dictId, Model model) {

        Dictionary dictionary = dictionaryService.findById(dictId);
        if (dictionary != null && dictionary.getParentId() != null) {
            return "redirect:/plugin/dictionary/" + dictionary.getParentId();
        }

        return "redirect:/plugin/dictionary";
    }

    @RequestMapping("/get")
    public String get(Long id, Model model) {

        model.addAttribute(ResultConst.RESULT, dictionaryService.findById(id));

        return "";
    }

    @RequestMapping("/add")
    public String add(Dictionary dictionary, Model model) {
        dictionaryService.insert(dictionary);
        return "";
    }

    @RequestMapping("/update")
    public String update(Dictionary dictionary, Model model) {

        dictionaryService.update(dictionary);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        dictionaryService.delete(ids);
        return "";
    }


}
