<#include "page-component.ftl">
<#macro listLayout title importCss=[] importJs=[]>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <#include "/fragment/head-css.ftl">

    <#if importCss??>
        <#list importCss as css>
<link rel="stylesheet" href="${rc.contextPath}/${css}"/>
        </#list>
    </#if>
</head>
<body>
<#if Application.renderHeader>
    <!-- 固定头部 -->
    <#include "${Application.pageHeader}">
</#if>

<#if Application.renderLeft>
    <!-- 左侧菜单 -->
    <#include "${Application.pageLeft}">
</#if>

<!-- 主页面 -->
<#if Application.renderLeft>
<div class="main col-sm-18 col-sm-offset-6 col-md-20 col-md-offset-4">
<#else>
<div class="main col-sm-24">
</#if>
    <#nested>
    <!-- 分页条 -->
    <#if data!?size &gt; 0>
        <#if pageInfo??>
            <@pagebar pageInfo=pageInfo queryForm='#query-form'/>
        </#if>
    </#if>
</div>

    <#include "/fragment/bottom-js.ftl">

    <#if importJs??>
        <#list importJs as js>
<script src="${rc.contextPath}/${js}"></script>
        </#list>
    </#if>
</body>
</html>

</#macro>