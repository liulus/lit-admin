package net.skeyurt.lit.web.controller;

import net.skeyurt.lit.commons.context.GlobalParam;
import net.skeyurt.lit.web.entity.Goods;
import net.skeyurt.lit.web.service.GoodsService;
import net.skeyurt.lit.web.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/3/19 15:18
 * version $Id: MainController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/index")
    public String index (GoodsVo vo, Model model) {

        model.addAttribute("goodsList", goodsService.queryPageList(vo));
        model.addAttribute("dbName", GlobalParam.get("dbName"));

        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public List<Goods> test (GoodsVo vo) {
        return  goodsService.queryPageList(vo);
    }



}
