<!DOCTYPE html>
<html lang="en">
<head>
    <title>菜单管理</title>

<#include "fragment/top-css.ftl">
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
            <li class="active">菜单管理</li>
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
            <button id="data-move" class="btn btn-sm btn-primary">
                <i class="fa fa-random"></i>&nbsp;&nbsp;移动
            </button>
            <button id="data-move-up" class="btn btn-sm btn-success">
                <i class="fa fa-chevron-up"></i>&nbsp;&nbsp;上移
            </button>
            <button id="data-move-down" class="btn btn-sm btn-info">
                <i class="fa fa-chevron-down"></i>&nbsp;&nbsp;下移
            </button>
        </#if>
        <#if menuVo.parentId??>
            <a href="${rc.contextPath}/plugin/menu/back/${menuVo.parentId?c}" class="btn btn-sm btn-warning">
                <i class="fa fa-reply"></i>&nbsp;&nbsp;返回上级
            </a>
        </#if>


        </div>

        <!-- 数据展示 -->
        <table class="table table-hover">
            <thead>
            <tr>
                <th class="text-center">
                    <input class="check-all" type="checkbox">
                </th>
                <!--<th>行号</th>-->
                <th>编码</th>
                <th>名称</th>
                <th>图标</th>
                <th>URL</th>
                <th>类型</th>
                <th>状态</th>
                <th>备注</th>
            </tr>
            </thead>

            <tbody>
            <#list result as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.menuId?c}">
                </td>
            <#--<td text="${item?counter}"></td>-->
                <td>
                    <a href="${rc.contextPath}/plugin/menu/${item.menuId?c}/child">${item.menuCode!?html}</a>
                </td>
                <td>${item.menuName!?html}</td>
                <td>${item.menuIcon!}</td>
                <td>${item.menuUrl!?html}</td>
                <td>${item.menuTypeStr!?html}</td>
                <td>
                    <div class="btn-group btn-group-xs" role="group">
                        <a class="data-enable btn ${item.enable?string('btn-success', 'btn-default')}">
                            <span class="${item.enable?string('', 'invisible')}">启用</span>
                        </a>
                        <a class="data-disable btn ${item.enable?string('btn-default', 'btn-danger')}">
                            <span class="${item.enable?string('invisible', '')}">禁用</span>
                        </a>
                    </div>
                </td>
                <td>${item.memo!?html}</td>
            </tr>
            <#else>
            <tr>
                <td colspan="9">
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
    <div class="text-center">
        <ul class="pagination">
            <li><a href="#" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
            <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">Next</a></li>
        </ul>
    </div>
</#if>
</div>

<div id="menu-tree" class="modal-body text-center" style="display: none">
    <ul class="ztree"></ul>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
        <#if menuVo.parentId??>
            <input type="hidden" name="parentId" value="${menuVo.parentId?c}">
        </#if>
            <input type="hidden" name="menuId" value="${r'${menuId}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单编码 :</span>
                <div class="col-sm-16">
                    <input type="text" name="menuCode" value="${r'${menuCode}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单名称 :</span>
                <div class="col-sm-16">
                    <input type="text" name="menuName" value="${r'${menuName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单图标 :</span>
                <div class="col-sm-16">
                    <input type="text" name="menuIcon" value="${r'${menuIcon}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6">菜单URL :</span>
                <div class="col-sm-16">
                    <input type="text" name="menuUrl" value="${r'${menuUrl}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单类型 :</span>
                <div class="col-sm-16">
                    <select name="menuType" class="form-control">
                        <#list menuType as item>
                            <option value="${item.dictKey!}" {@if menuType === '${item.dictKey!}'}selected {@/if}>${item.dictValue!}</option>
                        </#list>
                    </select>
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
<script src="${rc.contextPath}/js/menu.js"></script>
</body>
</html>
