package com.lit.service.user.controller;

import com.lit.service.user.model.CorporationVo;
import com.lit.service.user.service.CorporationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
