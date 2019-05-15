define(['vue', 'vueRouter', 'asyncImport', 'text!/api/plugin/route'], function (Vue, Router, _import, pluginRouteText) {
    Vue.use(Router);

    // 页面路由
    // var pageRoutes = [
    //     {path: '/404', component: _import('views/pages/404'), name: '404', meta: {title: '404未找到'}},
    //     {path: '/login', component: _import('views/pages/login'), name: 'login', meta: {title: '登录'}},
    // ]

    // 模块路由
    let mainRoutes = {
        name: 'index',
        path: '',
        component: _import('/js/views/layout-admin.js'),
        redirect: {name: 'home'}
    }
    let pluginRoute = JSON.parse(pluginRouteText);
    if (pluginRoute.success) {
        let pluginRouteArray = Array.isArray(pluginRoute.result)?pluginRoute.result:[pluginRoute.result]
        pluginRouteArray.forEach(route => {
            route.component = _import(route.component)
        })
        mainRoutes.children = pluginRoute.result
    } else {
        console.warn('未能加载所有页面路由, 请检查配置')
        mainRoutes.children = [{name: 'home', path: '/home', component: _import('/js/views/home.js')}]
    }

    return new Router({
        mode: 'hash',
        scrollBehavior: function () {
            return {y: 0}
        },
        routes: [mainRoutes]
    });
});
