<#-- 公共 css -->
<#macro topCss>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link rel="stylesheet" href="${rc.contextPath}/libs/bootstrap/3.3.7/css/default/bootstrap.min.css"/>
<link rel="stylesheet" href="${rc.contextPath}/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${rc.contextPath}/css/animate.css"/>

<link rel="stylesheet" href="${rc.contextPath}/css/lit-plugin.css"/>
<link rel="stylesheet" href="${rc.contextPath}/css/top-nav.css"/>
<link rel="stylesheet" href="${rc.contextPath}/css/left-menu.css"/>
</#macro>

<#-- 顶部导航 -->
<#macro topNav>
<nav id="top-nav" class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false"
                    aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Lit - Web</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
        </div>
    </div>
</nav>
</#macro>

<#-- 左侧菜单 -->
<#macro leftMenu>
<div class="col-sm-6 col-md-4 sidebar">
    <#list Application.menus>
        <ul class="nav nav-sidebar">
            <#items as menu>
                <#if menu.enable>
                    <li class="${menu.isParent?string('parent-menu', '')}">
                        <a href="<#if menu.isParent>#sub-item-1-${menu?counter}<#else>${rc.contextPath}${menu.menuUrl!}</#if>"
                           data-toggle="collapse">
                            <#if menu.isParent>
                                <i class="fa fa-pencil">&nbsp;&nbsp;</i>${menu.menuName}
                                <i class="glyphicon glyphicon-menu-left pull-right"></i>
                            </#if>
                        </a>
                        <#if menu.isParent>
                            <ul id="sub-item-1-${menu?counter}" class="collapse children-menu">
                                <#list menu.children as second>
                                    <#if second.enable>
                                        <li>
                                            <a href="${rc.contextPath}${second.menuUrl!}">
                                                <i class="fa fa-pencil">&nbsp;&nbsp;</i>${second.menuName!}
                                            </a>
                                        </li>
                                    </#if>
                                </#list>
                            </ul>
                        </#if>
                    </li>
                </#if>
            </#items>
        </ul>
    </#list>
</div>
</#macro>

<#-- 分页条 -->
<#macro pagebar pageInfo>
<div class="text-center">
    <ul class="pagination">
        <#list pageInfo.start..pageInfo.end>
            <li><a href="#" data-page-num="1">第一页${pageInfo.start}</a></li>
            <#items as index>
                <li><a href="#" data-page-num="${index?c}"
                       class="pager-num <#if index == pageInfo.pageNum>active</#if>">${index?c}</a></li>
            </#items>
            <li><a href="#" data-page-num="${pageInfo.totalPage?c}">最后一页${pageInfo.end}</a></li>
        </#list>
    </ul>
</div>
</#macro>


<#-- 公共 js -->
<#macro bottomJs>
<script type="text/template" id="alert-message-tpl">
    <div id="msg-${r'${msgCount}'}" class="alert alert-${r'${msgType}'} alert-dismissible alert-message animated fadeIn"
         role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        {@if msgType === 'success'}
        <i class="fa fa-check"></i>
        {@else if msgType === 'info'}
        <i class="fa fa-info"></i>
        {@else if msgType === 'warning'}
        <i class="fa fa-exclamation"></i>
        {@else if msgType === 'danger'}
        <i class="fa fa-times"></i>
        {@/if}
        &nbsp;&nbsp;
    ${r'${message}'}
    </div>
</script>
<script type="text/javascript">
    var path = '${rc.contextPath}';
</script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="${rc.contextPath}/js/html5shiv.min.js"></script>
<script src="${rc.contextPath}/js/respond.min.js"></script>
<![endif]-->
<script src="${rc.contextPath}/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="${rc.contextPath}/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${rc.contextPath}/libs/juicer/0.6.8-stable/juicer-min.js"></script>
<script src="${rc.contextPath}/libs/layer/3.0.3/layer.js"></script>
<script src="${rc.contextPath}/js/commons-func.js"></script>
<script src="${rc.contextPath}/js/left-menu.js"></script>
<script src="${rc.contextPath}/js/message.js"></script>
</#macro>