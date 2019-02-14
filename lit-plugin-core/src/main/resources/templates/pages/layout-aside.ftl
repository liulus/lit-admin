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