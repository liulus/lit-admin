package net.skeyurt.lit.web.controller;

import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.commons.page.PageInfo;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.web.service.GoodsService;
import net.skeyurt.lit.web.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/testErr")
    public String testEx(Model model) {

        goodsService.testEx();

        model.addAttribute(ResultConst.SUCCESS, true);
        return "test";
    }

    @RequestMapping("/index")
    public String index(GoodsVo vo, Model model) {

//        model.addAttribute("goodsList", goodsService.queryPageList(vo));

        PageInfo pageInfo = null;
        for (Object o : model.asMap().values()) {
            if (o instanceof PageList) {
                pageInfo = ((PageList) o).getPageInfo();
                break;
            }
        }

        if (pageInfo != null) {
            model.addAttribute("pageInfo", pageInfo);
        }

//        return "index";
        return "fileupload";
    }

    @RequestMapping("/upload")
    public String upload(GoodsVo vo, MultipartFile fileDemo, Model model) {



        return "";
    }

    @RequestMapping("/test")
    public String test(GoodsVo vo, HttpServletRequest request, RedirectAttributes model) {

        model.addAttribute("redi", "真的");
        model.addFlashAttribute("flash", "flash");

        if (StringUtils.endsWith(request.getRequestURI(), ".json")) {
            return "";
        }
        return "redirect:/main/testErr";
    }


}
