package com.github.lit.plugin.core.context;

import com.github.lit.plugin.core.model.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Liulu
 * @version v1.0
 * date 2019-05-15
 */
@RestController
@RequestMapping("/api")
public class PluginRouteController {

    @GetMapping("/plugin/route")
    public List<Route> getPluginRoutes() {
        return PluginRouteContext.getPluginRoutes();
    }

}
