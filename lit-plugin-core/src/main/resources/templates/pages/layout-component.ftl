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
                <el-menu-item v-if="!asideTop" index="1" @click="asideFold = !asideFold">
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
            <el-menu
                    v-if="asideMenuVisible"
                    class="aui-aside__menu"
                    :default-active="asideMenuActive"
                    :collapse="asideFold"
                    :unique-opened="true"
                    :collapse-transition="false"
                    :mode="asideTop ? 'horizontal' : 'vertical'">
                <el-menu-item index="home" @click="gotoPageHandle('@@path/index.html')">
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-home"></use></svg>
                    <span slot="title">首页</span>
                </el-menu-item>

                <el-menu-item index="icon" @click="gotoPageHandle('@@path/pages/icon.html')">
                    <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-gift"></use></svg>
                    <span slot="title">图标</span>
                </el-menu-item>

                <el-submenu index="basic" :popper-append-to-body="false">
                    <template slot="title">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-crown"></use></svg>
                        <span>基础页面</span>
                    </template>
                    <el-menu-item index="basic-login" @click="gotoPageHandle('@@path/pages/login.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>登录</span>
                    </el-menu-item>
                    <el-menu-item index="basic-login-v2" @click="gotoPageHandle('@@path/pages/login-v2.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>登录 - v2</span>
                    </el-menu-item>
                    <el-menu-item index="basic-register" @click="gotoPageHandle('@@path/pages/register.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>注册</span>
                    </el-menu-item>
                    <el-menu-item index="basic-register-v2" @click="gotoPageHandle('@@path/pages/register-v2.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>注册 - v2</span>
                    </el-menu-item>
                    <el-menu-item index="basic-forget" @click="gotoPageHandle('@@path/pages/forget.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>找回密码</span>
                    </el-menu-item>
                    <el-menu-item index="basic-forget-v2" @click="gotoPageHandle('@@path/pages/forget-v2.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>找回密码 - v2</span>
                    </el-menu-item>
                </el-submenu>

                <el-submenu index="chart" :popper-append-to-body="false">
                    <template slot="title">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-barchart"></use></svg>
                        <span>图表</span>
                    </template>
                    <el-menu-item index="chart-echarts" @click="gotoPageHandle('@@path/pages/echarts.html')">
                        <svg class="icon-svg aui-aside__menu-icon" aria-hidden="true"><use xlink:href="#icon-fire"></use></svg>
                        <span>Echarts</span>
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
    </aside>
</script>
<script>
    Vue.component('app-header', {
        template: '#app-header-template',
        data: function () {
            return {
                asideTop: false,
            }
        }
    })
    Vue.component('app-aside', {
        template: '#app-aside-template',
        data: function () {
            return {
                asideFold: false,
                // 侧边, 至头部状态
                asideTop: false,
                asideMenuVisible: true,
                asideMenuActive: 'chart'
            }
        }
    })
</script>