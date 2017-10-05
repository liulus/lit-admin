<#include "macro/plugin-macro.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户管理</title>
<#include "fragment/head-css.ftl">
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
            <li class="active">用户管理</li>
        </ol>
    </div>

    <!-- 查询 -->
    <div class="row">
        <form class="form-horizontal " action="" method="post" id="query-form">
            <div class="col-sm-12 col-md-9 form-group">
                <div class="col-sm-8 text-right control-label">关键字:</div>
                <div class="col-sm-16 ">
                    <input name="keyWord" class="form-control input-sm" type="text" value="${userVo.keyWord!}"
                           placeholder="请输入">
                </div>
            </div>
            <div class="col-sm-12 col-md-9 form-group">
                <div class="col-sm-8 text-right control-label">用户:</div>
                <div class="col-sm-16 ">
                    <input name="userId" class="form-control input-sm" type="text" value="${userVo.userId!}"
                           placeholder="请输入">
                </div>
            </div>
            <div class="col-sm-12 col-md-10 form-group">
                <div class="col-sm-10 text-right control-label">用户类型:</div>
                <div class="col-sm-14 ">
                    <select name="userType" class="form-control input-sm">
                        <option value=""></option>
                        <#--<option value="true" ${(userVo.system!false)?string('selected', '')}>是</option>-->
                        <#--<option value="false" ${(userVo.system!true)?string('', 'selected')}>否</option>-->
                    </select>
                </div>
            </div>
            <div class="col-sm-24 col-md-5 text-center form-group">
                <button id="data-query" class="btn btn-sm btn-primary">&nbsp;&nbsp;查询&nbsp;&nbsp;</button>
            </div>
        </form>
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
                <th>用户名</th>
                <th>昵称</th>
                <th>性别</th>
                <th>手机号</th>
                <th>用户状态</th>
            </tr>
            </thead>

            <tbody>
            <#list result as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.userId?c}">
                </td>
                <td>${item?counter}</td>
                <td>${item.userName!?html}</td>
                <td>${item.nickName!?html}</td>
                <td>${item.sex?string('男', '女')}</td>
                <td>${item.mobilePhone!}</td>
                <td>${item.userStatus!}</td>
            </tr>
            <#else>
            <tr>
                <td colspan="8">
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
    <@pagebar pageInfo=pageInfo queryForm='#query-form'></@pagebar>
</#if>

</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="userId" value="${r'${userId}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>参数码 :</span>
                <div class="col-sm-16">
                    <input type="text" name="userCode" value="${r'${userCode}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>参数值 :</span>
                <div class="col-sm-16">
                    <input type="text" name="userValue" value="${r'${userValue}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>启动加载 :</span>
                <div class="col-sm-16">
                    <select name="load" class="form-control">
                        <option value="true" {@if load} selected {@/if}>是 </option>
                        <option value="false" {@if !load} selected {@/if}>否 </option>
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
<script src="${rc.contextPath}/js/user.js"></script>
</body>
</html>