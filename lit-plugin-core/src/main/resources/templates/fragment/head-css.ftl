<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link rel="stylesheet" href="${rc.contextPath}/libs/bootstrap/3.3.7/css/default/bootstrap.min.css"/>
<link rel="stylesheet" href="${rc.contextPath}/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${rc.contextPath}/css/animate.css"/>

<link rel="stylesheet" href="${rc.contextPath}/css/lit-plugin.css"/>
<#if Application.renderHeader>
<link rel="stylesheet" href="${rc.contextPath}/css/top-nav.css"/>
</#if>
<#if Application.renderLeft>
<link rel="stylesheet" href="${rc.contextPath}/css/left-menu.css"/>
</#if>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="${rc.contextPath}/js/html5shiv.min.js"></script>
<script src="${rc.contextPath}/js/respond.min.js"></script>
<![endif]-->
<script type="text/javascript">
    var contextPath = '${rc.contextPath}';
    <#if RequestParameters['message']??>
    var message = '${RequestParameters['message']}'
    </#if>
</script>