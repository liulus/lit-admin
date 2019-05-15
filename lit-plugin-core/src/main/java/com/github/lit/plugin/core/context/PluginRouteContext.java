package com.github.lit.plugin.core.context;

import com.github.lit.plugin.core.model.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liulu
 * @version v1.0
 * date 2019-05-15
 */
public class PluginRouteContext {

    private static final List<Route> PLUGIN_ROUTES = new ArrayList<>();

    public synchronized static void addRoute(Route route) {
        if (route != null) {
            PLUGIN_ROUTES.add(route);
        }
    }

    public static List<Route> getPluginRoutes() {
        return PLUGIN_ROUTES;
    }


}
