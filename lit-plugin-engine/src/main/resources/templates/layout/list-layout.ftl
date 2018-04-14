<#include "page-component.ftl">
<#macro listLayout title importCss=[] importJs=[]>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <#include "../fragment/head-css.ftl">

    <#if importCss??>
        <#list importCss as css>
<link rel="stylesheet" href="${rc.contextPath}/${css}"/>
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
    <#if data!?size &gt; 0>
        <#if pageInfo??>
            <@pagebar pageInfo=pageInfo queryForm='#query-form'/>
        </#if>
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