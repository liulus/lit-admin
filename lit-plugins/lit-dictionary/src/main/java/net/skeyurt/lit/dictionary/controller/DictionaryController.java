package net.skeyurt.lit.dictionary.controller;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/4/8 20:39
 * version $Id: DictionaryController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/plugin/dictionary")
@Slf4j
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping({"/list", ""})
    public String list(DictionaryQo qo, Model model) {

        model.addAttribute(ResultConst.RESULT, dictionaryService.queryPageList(qo));
        return "dictionary";
    }

    @RequestMapping("/{parentId}/child")
    public String childList(DictionaryQo qo, @PathVariable Long parentId, Model model) {

        qo.setParentId(parentId);
        model.addAttribute(ResultConst.RESULT, dictionaryService.queryPageList(qo));
        return "dictionary";
    }

    @RequestMapping("/back/{dictId}")
    public String back(@PathVariable Long dictId, Model model) {

        Dictionary dictionary = dictionaryService.findById(dictId);
        if (dictionary != null && dictionary.getParentId() != null) {
            return "redirect:/plugin/dictionary/" + dictionary.getParentId() + "/child";
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
        dictionaryService.add(dictionary);
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
