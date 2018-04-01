<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='参数管理'importJs=['js/param.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">参数管理</li>
    </ol>
</div>

<!-- 查询表单 -->
<div class="row">
    <form class="form-horizontal " action="" method="get" id="query-form">
        <div class="col-sm-12 col-md-10 form-group">
            <div class="col-sm-8 text-right control-label">关键字:</div>
            <div class="col-sm-16 ">
                <input name="keyword" class="form-control input-sm" type="text" value="${paramQo.keyword!}"
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
    </div>

    <!-- 数据展示 -->
    <table id="data-result" class="table table-hover">
        <thead>
        <tr>
            <th class="text-center">
                <input class="check-all" type="checkbox">
            </th>
            <th>行号</th>
            <th>参数码</th>
            <th>参数值</th>
            <th>备注</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.paramId?c}">
                </td>
                <td>${item?counter}</td>
                <td>${item.code?html}</td>
                <td>${item.value!?html}</td>
                <td>${item.memo!?html}</td>
            </tr>
            <#else>
                <@AdminLayout.emptyData 5/>
            </#list>
        </tbody>
    </table>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="paramId" value="${r'${paramId}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>参数码 :</span>
                <div class="col-sm-16">
                    <input type="text" name="code" value="${r'${code}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>参数值 :</span>
                <div class="col-sm-16">
                    <input type="text" name="value" value="${r'${value}'}" class="form-control">
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

</@AdminLayout.listLayout>