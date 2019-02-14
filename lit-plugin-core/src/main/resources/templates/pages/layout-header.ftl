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