package com.github.lit.dictionary.controller;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.model.DictionaryVo;
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

    private static final String REDIRECT_PREFIX = "'" + PluginConst.REDIRECT + "/dictionary/list'";

    private static final String REDIRECT_EL = "+(#dictionary.parentId == 0L? '' : '?parentId=' + #dictionary.parentId)";
    @Resource
    private DictionaryService dictionaryService;

    @GetMapping
    @ViewName("dictionary")
    public List<DictionaryVo.List> childList(DictionaryQo qo, Model model) {
        if (qo.getParentId() == null) {
            qo.setParentId(0L);
        }
        if (qo.getParentId() != 0L) {
            Dictionary dictionary = dictionaryService.findById(qo.getParentId());
            model.addAttribute("returnId", dictionary == null ? 0 : dictionary.getParentId());
        }
        qo.setOrder(true);
        return BeanUtils.convert(DictionaryVo.List.class, dictionaryService.findPageList(qo));
    }

    @GetMapping("/{id}")
    public Dictionary get(@PathVariable Long id) {
        return dictionaryService.findById(id);
    }

    @PostMapping
    @ViewName(value = REDIRECT_PREFIX + REDIRECT_EL, spel = true)
    public Long add(DictionaryVo.Add dictionary) {
        return dictionaryService.insert(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @PutMapping
    @ViewName(value = REDIRECT_PREFIX + REDIRECT_EL, spel = true)
    public void update(DictionaryVo.Update dictionary) {
        dictionaryService.update(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @DeleteMapping
    @ViewName(REDIRECT_PREFIX)
    public void delete(Long[] ids) {
        dictionaryService.deleteByIds(ids);
    }


}
