<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='字典管理' importJs=['js/dictionary.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">字典管理</li>
    </ol>
</div>

<!-- 查询表单 -->
<div class="row">
    <form class="form-horizontal " action="" method="get" id="query-form">
        <div class="col-sm-12 col-md-10 form-group">
            <div class="col-sm-8 text-right control-label">关键字:</div>
            <div class="col-sm-16 ">
                <input name="keyword" class="form-control input-sm" type="text" value="${dictionaryQo.keyword!}"
                       placeholder="请输入">
            </div>
        </div>
        <div class="col-sm-12 col-md-4 text-center form-group">
            <@AdminLayout.queryBtn/>
        </div>
    </form>
</div>

<!-- 数据列表 -->
<div class="panel panel-default table-responsive">
    <!-- 数据操作 -->
    <div class="panel-heading">
        <@AdminLayout.addBtn/>
        <#if data?size &gt; 0>
            <@AdminLayout.updateBtn/>
            <@AdminLayout.deleteBtn/>
        </#if>
        <#if dictionaryQo.parentId != 0>
            <a href="${rc.contextPath}/plugin/dictionary?parentId=${returnId?c}"
               class="btn btn-sm btn-warning">
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
            <th>顺序号</th>
            <th>备注</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.id?c}">
                </td>
                <td>${item?counter}</td>
                <td>
                    <a href="${rc.contextPath}/plugin/dictionary?parentId=${item.id?c}">${item.dictKey!?html}</a>
                </td>
                <td>${item.dictValue!?html}</td>
                <td>${item.orderNum!?c}</td>
                <td>${item.remark!?html}</td>
            </tr>
            <#else>
                <@AdminLayout.emptyData 6/>
            </#list>
        </tbody>
    </table>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal">
            <input type="hidden" name="parentId" value="${dictionaryQo.parentId?c}">
            <input type="hidden" name="id" value="${r'${id}'}">
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
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>顺序号 :</span>
                <div class="col-sm-16">
                    <input type="number" name="orderNum" value="${r'${orderNum}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>备注 :</span>
                <div class="col-sm-16">
                    <textarea name="remark" class="form-control" rows="3">${r'${remark}'}</textarea>
                </div>
            </div>
        </form>
    </div>
</script>

</@AdminLayout.listLayout>