package com.github.lit.dictionary.controller;

import com.github.lit.bean.BeanUtils;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.model.DictionaryQo;
import com.github.lit.dictionary.model.DictionaryVo;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.plugin.core.constant.AuthorityConst;
import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.spring.web.annotation.ViewName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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

    private static final String REDIRECT_PREFIX = "'" + PluginConst.REDIRECT + "/dictionary'";

    private static final String REDIRECT_EL = "+(#dictionary.parentId == 0L? '' : '?parentId=' + #dictionary.parentId)";
    @Resource
    private DictionaryService dictionaryService;

    @GetMapping
    @ViewName("dictionary")
    @Secured(AuthorityConst.VIEW_DICTIONARY)
    public List<DictionaryVo.Detail> childList(DictionaryQo qo, Model model) {
        int i = 1/0;
        if (qo.getParentId() != 0L) {
            Dictionary dictionary = dictionaryService.findById(qo.getParentId());
            model.addAttribute("returnId", dictionary == null ? 0 : dictionary.getParentId());
        }
        qo.setOrder(true);
        return BeanUtils.convert(DictionaryVo.Detail.class, dictionaryService.findPageList(qo));
    }

    @GetMapping("/{id}")
    @Secured(AuthorityConst.VIEW_DICTIONARY)
    public DictionaryVo.Detail get(@PathVariable Long id) {
        return BeanUtils.convert(dictionaryService.findById(id), new DictionaryVo.Detail());
    }

    @PostMapping
    @ViewName(value = REDIRECT_PREFIX + REDIRECT_EL, spel = true)
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public Long add(DictionaryVo.Add dictionary) {
        return dictionaryService.insert(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @PutMapping
    @ViewName(value = REDIRECT_PREFIX + REDIRECT_EL, spel = true)
    @Secured(AuthorityConst.MODIFY_DICTIONARY)
    public void update(DictionaryVo.Update dictionary) {
        dictionaryService.update(BeanUtils.convert(dictionary, new Dictionary()));
    }

    @DeleteMapping
    @ViewName(REDIRECT_PREFIX)
    @Secured(AuthorityConst.REMOVE_DICTIONARY)
    public void delete(Long[] ids) {
        dictionaryService.deleteByIds(ids);
    }


}
