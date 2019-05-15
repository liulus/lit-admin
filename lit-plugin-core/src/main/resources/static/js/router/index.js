define(['vue', 'vueRouter', 'asyncImport'], function (Vue, Router, _import) {
    Vue.use(Router);

    // 页面路由
    // var pageRoutes = [
    //     {path: '/404', component: _import('views/pages/404'), name: '404', meta: {title: '404未找到'}},
    //     {path: '/login', component: _import('views/pages/login'), name: 'login', meta: {title: '登录'}},
    // ]


    // 模块路由
    var moduleRoutes = {
        name: 'index',
        path: '',
        component: _import('/js/views/layout-admin.js'),
        redirect: {name: 'home'},
        meta: {title: '布局'},
        children: [
            {path: '/home', component: _import('/js/views/home.js'), name: 'home', meta: {title: '首页', isTab: true}},
            {path: '/menu', component: _import('/js/menu.js'), name: 'menu', meta: {title: '首页', isTab: true}},
        ],
    }
    var router = new Router({
        mode: 'hash',
        scrollBehavior: function () {
            return {y: 0}
        },
        routes: [moduleRoutes],
    });

    return router;
});
