<#include "macro/plugin-macro.ftl">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>字典管理</title>
<@topCss></@topCss>
</head>
<body>
<!-- 固定头部 -->
<@topNav></@topNav>
<!-- 左侧菜单 -->
<@leftMenu></@leftMenu>

<!-- 主页面 -->
<div class="main col-sm-18 col-sm-offset-6 col-md-20 col-md-offset-4">
<#assign emptyResult = result?size == 0>

    <!-- 导航条 -->
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li class="active">系统管理</li>
            <li class="active">字典管理</li>
        </ol>
    </div>

    <!-- 查询 -->
    <div class="row">
        <form class="form-horizontal " action="" method="post" id="query-form">
            <div class="col-sm-12 col-md-9 form-group">
                <div class="col-sm-8 text-right control-label">关键字:</div>
                <div class="col-sm-16 ">
                    <input name="keyWord" class="form-control input-sm" type="text" value="${dictionaryQo.keyWord!}"
                           placeholder="请输入">
                </div>
            </div>
            <div class="col-sm-12 col-md-10 form-group">
                <div class="col-sm-10 text-right control-label">是否系统级:</div>
                <div class="col-sm-14 ">
                    <select name="system" class="form-control input-sm">
                        <option value=""></option>
                        <option value="true" ${(dictionaryQo.system!false)?string('selected', '')}>是</option>
                        <option value="false" ${(dictionaryQo.system!true)?string('', 'selected')}>否</option>
                    </select>
                </div>
            </div>
        <#--<input type="hidden" name="pageNum" value="${dictionaryQo.pageNum!}">-->
        <#--<input type="hidden" name="pageSize" value="${dictionaryQo.pageSize!}">-->
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
        <#if dictionaryQo.parentId??>
            <a href="${rc.contextPath}/plugin/dictionary/back/${dictionaryQo.parentId?c}" class="btn btn-sm btn-warning">
                <i class="fa fa-reply"></i>&nbsp;&nbsp;返回上级
            </a>
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
                <th>字典Key</th>
                <th>字典值</th>
                <th>是否系统级</th>
                <th>备注</th>
            </tr>
            </thead>

            <tbody>
            <#list result as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.dictId?c}">
                </td>
                <td>${item?counter}</td>
                <td>
                    <a href="${rc.contextPath}/plugin/dictionary/${item.dictId?c}">${item.dictKey!?html}</a>
                </td>
                <td>${item.dictValue!?html}</td>
                <td>${item.system?string('是', '否')}</td>
                <td>${item.memo!?html}</td>
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
    <@pagebar pageInfo=pageInfo queryForm='#query-form'></@pagebar>
</#if>

</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
        <#if dictionaryQo.parentId??>
            <input type="hidden" name="parentId" value="${dictionaryQo.parentId?c}">
        </#if>
            <input type="hidden" name="dictId" value="${r'${dictId}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>字典key :</span>
                <div class="col-sm-16">
                    <input type="text" name="dictKey" value="${r'${dictKey}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>字典值 :</span>
                <div class="col-sm-16">
                    <input type="text" name="dictValue" value="${r'${dictValue}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>是否系统级 :</span>
                <div class="col-sm-16">
                    <select name="system" class="form-control">
                        <option value="true" {@if system} selected {@/if}>是 </option>
                        <option value="false" {@if !system} selected {@/if}>否 </option>
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

<@bottomJs></@bottomJs>
<script src="${rc.contextPath}/js/dictionary.js"></script>
</body>
</html>