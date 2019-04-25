<#macro adminLayout title='title'>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">

    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <#--<link href="https://cdn.bootcss.com/normalize/8.0.1/normalize.css" rel="stylesheet">-->
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/styles/lit-common.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1156831_xn4orqpa3ee.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/element-theme/cyan/index.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/styles/aui-cyan.css">
    <script type="text/javascript">
        let contextPath = '${springMacroRequestContext.contextPath}'
    </script>
</head>
<body>
<div v-cloak ref="auiWrapper" id="app" class="aui-wrapper"
     :class="[
        'aui-header--' + headerSkin,
        'aui-aside--' + asideSkin,
        {
          'aui-header--fixed': headerFixed,
          'aui-aside--fixed': asideFixed,
          'aui-aside--fold': asideFold,
          'aui-aside--top': asideTop,
          'aui-control--open': controlOpen,
          'aui-control--fixed': controlFixed
        }
      ]"
     v-loading.fullscreen.lock="loading" element-loading-text="拼命加载中">

    <template v-if="!loading">
        <!-- aui-header -->
        <#--<#include "layout-header.ftl">-->
        <app-header :aside-top="asideTop"
                    @update:control-open="controlOpen = !controlOpen"
                    @update:aside-fold="asideFold = !asideFold"></app-header>

        <!-- aui-aside -->
        <#--<#include "layout-aside.ftl">-->
        <app-aside :aside-fold="asideFold" :aside-top="asideTop" :aside-menu-visible="asideMenuVisible"></app-aside>

        <!-- aui-control -->
        <aside class="aui-control">
            <div class="aui-control__inner">
                <div class="aui-control__bd">
                    <el-tabs class="aui-tabs aui-tabs--flex" v-model="controlTabsActive">
                        <el-tab-pane label="Layout" name="layout">
                            <dl class="aui-control__setting">
                                <dt>Header</dt>
                                <dd><el-checkbox v-model="headerFixed">Fixed 固定</el-checkbox></dd>
                                <dd><el-checkbox v-model="headerSkin" true-label="colorful" false-label="white">Colorful 鲜艳</el-checkbox></dd>
                            </dl>
                            <dl class="aui-control__setting">
                                <dt>Aside</dt>
                                <dd><el-checkbox v-model="asideFixed">Fixed 固定</el-checkbox></dd>
                                <dd><el-checkbox v-model="asideSkin" true-label="dark" false-label="white">Dark 鲜艳</el-checkbox></dd>
                                <dd><el-checkbox v-model="asideTop">Top 至头部</el-checkbox></dd>
                            </dl>
                            <dl class="aui-control__setting">
                                <dt>Control</dt>
                                <dd><el-checkbox v-model="controlFixed">Fixed 固定</el-checkbox></dd>
                            </dl>
                        </el-tab-pane>
                        <el-tab-pane label="Skins" name="skins">
                            <dl class="aui-control__setting">
                                <dt>Skins</dt>
                                <dd v-for="item in skinList" :key="item.name">
                                    <el-radio v-model="skin" :label="item.name" @change="handleSkinChange">
                                        <span class="t-capitalize">{{ item.name }}</span> {{ item.remark }}
                                    </el-radio>
                                </dd>
                            </dl>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </aside>


        <!-- aui-main -->
        <app-main></app-main>

        <!-- aui-footer -->
        <#--<footer class="aui-footer">-->
            <#--<p><a href="#" target="_blank">lit admin</a>2018 © lit admin</p>-->
        <#--</footer>-->
    </template>
</div>

<#--<script src="${rc.contextPath}/libs/vue-2.5.17/vue.js"></script>-->
<script src="https://cdn.bootcss.com/vue/2.6.3/vue.js"></script>
<#--<script src="${rc.contextPath}/libs/element-2.4.5/index.js"></script>-->
<#--<script src="https://cdn.bootcss.com/element-ui/2.5.4/index.js"></script>-->
<script src="https://cdn.bootcss.com/element-ui/2.7.2/index.js"></script>
<script src="${springMacroRequestContext.contextPath}/js/lit-common.js"></script>
<#include "layout-component.ftl">
    <#nested>
<script>
    var vm = new Vue({
        el: '#app',
        data: function () {
            return {
                loading: true,
                headerSkin: 'colorful', // 头部, 皮肤 (white 白色 / colorful 鲜艳)
                headerFixed: false, // 头部, 固定状态
                asideSkin: 'white', // 侧边, 皮肤 (white 白色 / dark 黑色)
                asideFixed: false, // 侧边, 固定状态
                asideFold: sessionStorage.getItem('asideFold') === 'true', // 侧边, 折叠状态
                asideTop: false, // 侧边, 至头部状态
                controlFixed: false,
                asideMenuVisible: true, // 侧边, 菜单显示状态 (控制台“至头部”操作时, el-menu组件需根据mode属性重新渲染)
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
            this.initSkin(this.skin)
            this.headerSkin = configure.headerSkin || 'colorful'
            this.asideSkin = configure.asideSkin || 'white'
            this.headerFixed = configure.headerFixed || false
            this.asideFixed = configure.asideFixed || false
            this.asideTop = configure.asideTop || false
            this.controlFixed = configure.controlFixed || false
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
            asideFold(value) {
                sessionStorage.setItem('asideFold', value)
            },
            asideTop(value) {
                this.setLayoutConfigure('asideTop', value)
            },
            controlFixed(value) {
                this.setLayoutConfigure('controlFixed', value)
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
                this.initSkin(value)
                this.setLayoutConfigure('skin', value)
            },
            initSkin(val) {
                let styleList = [
                    {
                        id: 'J_elementTheme',
                        url: contextPath + '/element-theme/' + val + '/index.css?t=' + new Date().getTime()
                    }, {
                        id: 'J_auiSKin',
                        url: contextPath + '/styles/aui-' + val + '.min.css?t=' + new Date().getTime()
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
            }
        }
    });
</script>
</body>
</html>
</#macro>