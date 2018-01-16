<#include "macro/plugin-macro.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>角色管理</title>
<#include "fragment/head-css.ftl">
    <link rel="stylesheet" href="${rc.contextPath}/libs/zTree/3.5/css/metroStyle/metroStyle.css">
<#--<link rel="stylesheet" href="${rc.contextPath}/libs/zTree/3.5/css/zTreeStyle/zTreeStyle.css">-->

</head>
<body>
<!-- 固定头部 -->
<#include "fragment/top-nav.ftl">
<!-- 左侧菜单 -->
<#include "fragment/left-menu.ftl">

<!-- 主页面 -->
<div class="main col-sm-18 col-sm-offset-6 col-md-20 col-md-offset-4">
<#assign emptyResult = result?size == 0>

    <!-- 导航条 -->
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li class="active">系统管理</li>
            <li class="active">角色管理</li>
        </ol>
    </div>

    <div class="panel panel-default table-responsive">
        <!-- 数据操作 -->
        <div class="panel-heading">
            <button id="data-add" class="btn btn-sm btn-success">
                <i class="fa fa-plus"></i>&nbsp;&nbsp;增加
            </button>
        <#if !emptyResult>
            <button id="data-del" class="btn btn-sm btn-danger">
                <i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除
            </button>
            <button id="data-update" class="btn btn-sm btn-info">
                <i class="fa fa-pencil"></i>&nbsp;&nbsp;修改
            </button>
        </#if>
        </div>

        <!-- 数据展示 -->
        <table id="data-result" class="table table-hover">
            <thead>
            <tr>
                <th class="text-center">
                    <input class="check-all" type="checkbox">
                </th>
                <th>行号</th>
                <th>编号</th>
                <th>角色名</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <#list result as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.roleId?c}">
                </td>
                <td>${item?counter}</td>
                <td>${item.roleCode!?html}</td>
                <td>${item.roleName!?html}</td>
                <td>${item.memo!?html}</td>
                <td>
                    <a class="data-authorize btn btn-xs btn-primary">
                        <i class="fa fa-random"></i>&nbsp;分配权限
                    </a>
                </td>
            </tr>
            <#else>
            <tr>
                <td colspan="6">
                    <div class="no-data text-center">
                        <span><i class="fa fa-info-circle fa-1g"></i>&nbsp;&nbsp;没有数据</span>
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <!-- 分页条 -->
<#if !emptyResult>
    <@pagebar pageInfo=pageInfo></@pagebar>
</#if>
</div>

<div id="authority-tree" class="modal-body text-center" style="display: none">
    <ul class="ztree"></ul>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="roleId" value="${r'${roleId}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>角色编号 :</span>
                <div class="col-sm-16">
                    <input type="text" name="roleCode" value="${r'${roleCode}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>角色名 :</span>
                <div class="col-sm-16">
                    <input type="text" name="roleName" value="${r'${roleName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>备注 :</span>
                <div class="col-sm-16">
                    <textarea name="memo" class="form-control" rows="3">${r'${memo}'}</textarea>
                </div>
            </div>
        </form>
    </div>
</script>

<#include "fragment/bottom-js.ftl">
<script src="${rc.contextPath}/libs/zTree/3.5/js/ztree.all.min.js"></script>
<script src="${rc.contextPath}/js/role.js"></script>
</body>
</html>


