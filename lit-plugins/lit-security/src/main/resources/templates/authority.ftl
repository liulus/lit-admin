<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='权限管理' 'js/authority.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">权限管理</li>
    </ol>
</div>

<!-- 数据列表 -->
<div class="panel panel-default table-responsive">
    <!-- 数据操作 -->
    <#--<div class="panel-heading">-->
        <#--<@AdminLayout.addBtn/>-->
        <#--<#if data?size &gt; 0>-->
            <#--<@AdminLayout.updateBtn/>-->
            <#--<@AdminLayout.deleteBtn/>-->
        <#--</#if>-->
    <#--</div>-->

    <!-- 数据展示 -->
    <table id="data-result" class="table table-hover">
        <thead>
        <tr>
            <th class="text-center">
                <input class="check-all" type="checkbox">
            </th>
            <th>行号</th>
            <th>权限码</th>
            <th>名称</th>
            <th>模块</th>
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
                <td>${item.code!?html}</td>
                <td>${item.name!?html}</td>
                <td>${item.module!?html}</td>
                <td>${item.remark!?html}</td>
            </tr>
            <#else>
                <@AdminLayout.emptyData 6/>
            </#list>
        </tbody>
    </table>
</div>

</@AdminLayout.listLayout>


