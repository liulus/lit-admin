<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='角色管理'
importCss==['libs/zTree/3.5/css/metroStyle/metroStyle.css']
importJs=['libs/zTree/3.5/js/ztree.all.min.js', 'js/role.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">参数管理</li>
    </ol>
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
            <th>编号</th>
            <th>角色名</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.id?c}">
                </td>
                <td>${item?counter}</td>
                <td>${item.code!?html}</td>
                <td>${item.name!?html}</td>
                <td>${item.remark!?html}</td>
                <td>
                    <a class="data-authorize btn btn-xs btn-primary">
                        <i class="fa fa-random"></i>&nbsp;分配权限
                    </a>
                </td>
            </tr>
            <#else>
                <@AdminLayout.emptyData 6/>
            </#list>
        </tbody>
    </table>
</div>

<div id="authority-tree" class="modal-body text-center" style="display: none">
    <ul class="ztree"></ul>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="id" value="${r'${id}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>角色编号 :</span>
                <div class="col-sm-16">
                    <input type="text" name="code" value="${r'${code}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>角色名 :</span>
                <div class="col-sm-16">
                    <input type="text" name="name" value="${r'${name}'}" class="form-control">
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


