package com.github.lit.user.controller;

import com.github.lit.user.model.CorporationVo;
import com.github.lit.user.service.CorporationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
@RestController
@RequestMapping("/api/corporation")
public class CorporationController {

    @Resource
    private CorporationService corporationService;

    @PostMapping("/info")
    public Long saveCorporation(@RequestBody CorporationVo.Info corporationInfo) {
        return corporationService.saveCorporation(corporationInfo);
    }

    @GetMapping("/info")
    public CorporationVo.Info getCorporationInfo() {
        return corporationService.getCorporationInfo();
    }

}
