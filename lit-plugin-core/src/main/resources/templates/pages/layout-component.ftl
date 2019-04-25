<#-- header -->
<script type="text/html" id="app-header-template">
    <header class="aui-header">
        <div class="aui-header__hd">
            <a class="aui-brand aui-brand--lg" href="#">Lit-Admin</a>
            <a class="aui-brand aui-brand--sm" href="#">Lit</a>
        </div>
        <div class="aui-header__bd">
            <!-- aui-header__menu left -->
            <el-menu class="aui-header__menu aui-header__menu--left" mode="horizontal">
                <el-menu-item v-if="!asideTop" index="1" @click="$emit('update:aside-fold', '')">
                    <svg class="icon-svg aui-header__icon-menu aui-header__icon-menu--rz180" aria-hidden="true">
                        <use xlink:href="#icon-outdent"></use>
                    </svg>
                </el-menu-item>
            </el-menu>
            <!-- aui-header__menu right -->
            <el-menu class="aui-header__menu aui-header__menu--right" mode="horizontal">
                <el-submenu index="3" :popper-append-to-body="false">
                    <template slot="title">
                        <img class="aui-avatar" src="${rc.contextPath}/images/avatar.png">
                        <span>admin</span>
                    </template>
                    <el-menu-item index="3-1">修改密码</el-menu-item>
                    <el-menu-item index="3-2">退出</el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
    </header>
</script>

<#-- 侧边菜单 -->
<script type="text/html" id="app-aside-template">
    <aside class="aui-aside">
        <div class="aui-aside__inner">
            <el-menu v-if="asideMenuVisible"
                    class="aui-aside__menu"
                    :default-active="activeMenuIndex"
                    :collapse="asideFold"
                    :collapse-transition="false"
                    :mode="asideTop ? 'horizontal' : 'vertical'"
                    @select="select">
                <el-menu-item index="home">
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-home"></use></svg>
                    <span slot="title">首页</span>
                </el-menu-item>

                <template v-for="(item, index) in menuList">
                    <el-submenu v-if="item.isParent" :index="String(item.id)" :popper-append-to-body="false">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{item.name}}</span>
                        </template>
                        <template v-for="(subItem, subIndex) in item.children">
                            <el-menu-item :index="String(subItem.id)">
                                <i :class="subItem.icon"></i>
                                <span slot="title">{{subItem.name}}</span>
                            </el-menu-item>
                        </template>
                    </el-submenu>
                    <el-menu-item v-else :index="String(item.id)">
                        <i :class="item.icon"></i>
                        <span slot="title">{{item.name}}</span>
                    </el-menu-item>
                </template>
            </el-menu>
        </div>
    </aside>
</script>

<script type="text/html" id="app-breadcrumb-template">
    <div class="aui-main__hd">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item>
                <i class="ic ichome"></i>
            </el-breadcrumb-item>
            <el-breadcrumb-item>{{title}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</script>


<script>
    Vue.component('app-breadcrumb', {
        template: '#app-breadcrumb-template',
        props: {
            title: String
        }
    })
    Vue.component('app-header', {
        template: '#app-header-template',
        props: {
            asideTop: Boolean
        }
    })
    Vue.component('app-aside', {
        template: '#app-aside-template',
        props: {
            asideFold: Boolean,
            asideTop: Boolean,
            asideMenuVisible: Boolean,
        },
        data() {
            return {
                menuList: [],
                activeMenuIndex: sessionStorage.getItem('activeMenuIndex')
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {
                HttpRequest.get('/api/my/menu').then(res => {
                    if (res.success) {
                        this.menuList = res.result
                    }
                })
            },
            select(index, indexPath) {
                sessionStorage.setItem('activeMenuIndex', index)
                this.menuList.forEach(menu => {
                    if (String(menu.id) === index) {
                        this.redirect(menu)
                    } else if (menu.isParent) {
                        menu.children.forEach( subMenu => {
                            if (String(subMenu.id) === index) {
                                this.redirect(subMenu)
                            }
                        })
                    }
                })
            },
            redirect(menu) {
                window.location.href = contextPath + menu.url
            }
        }
    })
</script>