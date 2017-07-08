package net.skeyurt.lit.dictionary.controller;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.qo.DictionaryQo;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/4/8 20:39
 * version $Id: DictionaryController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/dictionary")
@Slf4j
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping({"/list", ""})
    public String list(DictionaryQo qo, Model model) {

        model.addAttribute(ResultConst.RESULT, dictionaryService.queryPageList(qo));
        return "dictionary";
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


}
