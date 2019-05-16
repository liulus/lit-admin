define(['text!/html/layout-admin.html',], function (tmpl) {
    return {
        template: tmpl,
        data: function () {
            return {
                loading: true,
                menuList: [],
                headerSkin: 'colorful', // 头部, 皮肤 (white 白色 / colorful 鲜艳)
                headerFixed: false, // 头部, 固定状态
                asideSkin: 'white', // 侧边, 皮肤 (white 白色 / dark 黑色)
                asideFixed: false, // 侧边, 固定状态
                asideFold: sessionStorage.getItem('asideFold') === 'true', // 侧边, 折叠状态
                asideTop: false, // 侧边, 至头部状态
                controlFixed: false,
                asideMenuVisible: true, // 侧边, 菜单显示状态 (控制台“至头部”操作时, el-menu组件需根据mode属性重新渲染)
                activeMenuIndex: sessionStorage.getItem('activeMenuIndex') || 'home',
                controlTabsActive: 'layout',
                controlOpen: false,
                skin: 'cyan',  // 皮肤, 默认值
                // 皮肤, 列表
                skinList: [
                    {name: 'blue', color: '#3E8EF7', remark: '蓝色'},
                    {name: 'brown', color: '#997B71', remark: '棕色'},
                    {name: 'cyan', color: '#0BB2D4', remark: '青色'},
                    {name: 'gray', color: '#757575', remark: '灰色'},
                    {name: 'green', color: '#11C26D', remark: '绿色'},
                    {name: 'indigo', color: '#667AFA', remark: '靛青色'},
                    {name: 'orange', color: '#EB6709', remark: '橙色'},
                    {name: 'pink', color: '#F74584', remark: '粉红色'},
                    {name: 'purple', color: '#9463F7', remark: '紫色'},
                    {name: 'red', color: '#FF4C52', remark: '红色'},
                    {name: 'turquoise', color: '#17B3A3', remark: '蓝绿色'},
                    {name: 'yellow', color: '#FCB900', remark: '黄色'}
                ]
            }
        },
        created() {
            let configure = this.getLayoutConfigure()
            this.skin = configure.skin || 'cyan'
            if (this.skin !== 'cyan') {
                this.initSkin(this.skin)
            }
            this.headerSkin = configure.headerSkin || 'colorful'
            this.asideSkin = configure.asideSkin || 'white'
            this.headerFixed = configure.headerFixed || false
            this.asideFixed = configure.asideFixed || false
            this.asideTop = configure.asideTop || false
            this.controlFixed = configure.controlFixed || false
            this.initMenu()
            this.loading = false;
        },
        watch: {
            headerSkin(value) {
                this.setLayoutConfigure('headerSkin', value)
            },
            asideSkin(value) {
                this.setLayoutConfigure('asideSkin', value)
            },
            headerFixed(value) {
                this.setLayoutConfigure('headerFixed', value)
            },
            asideFixed(value) {
                this.setLayoutConfigure('asideFixed', value)
            },
            asideTop(value) {
                this.setLayoutConfigure('asideTop', value)
            },
            controlFixed(value) {
                this.setLayoutConfigure('controlFixed', value)
            },
            asideFold(value) {
                sessionStorage.setItem('asideFold', value)
            }
        },
        methods: {
            getLayoutConfigure() {
                return JSON.parse(localStorage.getItem('layoutConfigure')) || {}
            },
            setLayoutConfigure(key, value) {
                let configure = this.getLayoutConfigure()
                configure[key] = value
                localStorage.setItem('layoutConfigure', JSON.stringify(configure))
            },
            handleSkinChange(val) {
                this.initSkin(val)
                this.setLayoutConfigure('skin', val)
            },
            initSkin(val) {
                let styleList = [
                    {
                        id: 'J_elementTheme',
                        url: contextPath + '/libs/element/2.4.5/themes/' + val + '/index.css?t=' + new Date().getTime()
                    }, {
                        id: 'J_auiSKin',
                        url: contextPath + '/styles/themes/aui-' + val + '.css?t=' + new Date().getTime()
                    }
                ];
                for (var i = 0; i < styleList.length; i++) {
                    var el = document.querySelector('#' + styleList[i].id);
                    if (el) {
                        el.href = styleList[i].url;
                        continue;
                    }
                    el = document.createElement('link');
                    el.id = styleList[i].id;
                    el.href = styleList[i].url;
                    el.rel = 'stylesheet';
                    document.querySelector('head').appendChild(el);
                }
            },
            initMenu() {
                let myMenu = sessionStorage.getItem('myMenu');
                if (myMenu) {
                    this.menuList = JSON.parse(myMenu)
                } else {
                    HttpRequest.get('/api/my/menu').then(res => {
                        if (res.success) {
                            this.menuList = res.result || []
                            sessionStorage.setItem('myMenu', JSON.stringify(this.menuList))
                        }
                    })
                }
            },
            select(index, indexPath) {
                sessionStorage.setItem('activeMenuIndex', index)
                if(index === 'home') {
                    this.redirect({url: '/home'})
                    return
                }
                this.menuList.forEach(menu => {
                    if (String(menu.id) === index) {
                        this.redirect(menu)
                    } else if (menu.isParent) {
                        menu.children.forEach(subMenu => {
                            if (String(subMenu.id) === index) {
                                this.redirect(subMenu)
                            }
                        })
                    }
                })
            },
            redirect(menu) {
                this.$router.push(menu.url)
                // window.location.href = contextPath + menu.url
            }
        }
    };
});
