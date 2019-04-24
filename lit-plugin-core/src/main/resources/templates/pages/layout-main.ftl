<#macro adminLayout title='title' importCss=[]>
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
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1156831_m6l90la5qsb.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/element-theme/cyan/index.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/styles/aui-cyan.css">
    <#if importCss??>
        <#list importCss as css>
<link rel="stylesheet" href="${rc.contextPath}/${css}"/>
        </#list>
    </#if>
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
          'aui-aside--top': asideTop
        }
      ]"
     v-loading.fullscreen.lock="loading" element-loading-text="拼命加载中">

    <template v-if="!loading">
        <!-- aui-header -->
        <#--<#include "layout-header.ftl">-->
        <app-header :aside-top="asideTop" @update:aside-fold="asideFold = !asideFold"></app-header>

        <!-- aui-aside -->
        <#--<#include "layout-aside.ftl">-->
        <app-aside :aside-fold="asideFold" :aside-top="asideTop" :aside-menu-visible="asideMenuVisible"></app-aside>

        <!-- aui-main -->
        <main class="aui-main">
            <app-main></app-main>
        </main>

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
<script src="${rc.contextPath}/js/lit-common.js"></script>
<script src="${rc.contextPath}/icons/iconfont.js"></script>
<#include "layout-component.ftl">
    <#nested>
<script>
    var vm = new Vue({
        el: '#app',
        data: function () {
            return {
                // 加载中
                loading: true,
                // 头部, 皮肤 (white 白色 / colorful 鲜艳)
                headerSkin: 'colorful',
                // 头部, 固定状态
                headerFixed: false,
                // 侧边, 皮肤 (white 白色 / dark 黑色)
                asideSkin: 'white',
                // 侧边, 固定状态
                asideFixed: false,
                // 侧边, 折叠状态
                asideFold: false,
                // 侧边, 至头部状态
                asideTop: false,
                // 侧边, 菜单显示状态 (控制台“至头部”操作时, el-menu组件需根据mode属性重新渲染)
                asideMenuVisible: true,
                // 主内容, 展示类型 (standard 标准 / tabs 标签页)
                mainType: 'standard',
                // 皮肤, 默认值
                skin: 'cyan',
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
            this.loading = false;
        }
    });
</script>
</body>
</html>
</#macro>