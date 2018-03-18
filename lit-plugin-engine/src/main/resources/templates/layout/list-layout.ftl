<#macro listLayout title importCss=[] importJs=[]>
    <#include "../macro/plugin-macro.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <#include "../fragment/head-css.ftl">
    <#if importCss??>
        <#list importCss as css>
            <script src="${rc.contextPath}/${css}"></script>
        </#list>
    </#if>
</head>
<body>
<!-- 固定头部 -->
    <#include "../fragment/top-nav.ftl">

<!-- 左侧菜单 -->
    <#include "../fragment/left-menu.ftl">

<!-- 主页面 -->
<div class="main col-sm-18 col-sm-offset-6 col-md-20 col-md-offset-4">
    <#nested>

    <!-- 分页条 -->
    <#if data?size &gt; 0>
        <@pagebar pageInfo=pageInfo queryForm='#query-form'></@pagebar>
    </#if>
</div>

    <#include "../fragment/bottom-js.ftl">

    <#if importJs??>
        <#list importJs as js>
<script src="${rc.contextPath}/${js}"></script>
        </#list>
    </#if>
</body>
</html>

</#macro>