<#macro adminLayout title='title'>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <#include "html-header.ftl"/>

</head>
<body>
<noscript>
    <strong>We're sorry but we doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
</noscript>
<div id="app"><app></app></div>

<#include "html-script.ftl"/>
<#nested>
<#include "layout-amdin-component.ftl"/>
</body>
</html>
</#macro>