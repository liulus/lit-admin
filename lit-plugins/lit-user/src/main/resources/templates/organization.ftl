<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='组织管理' importJs=['js/organization.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">组织管理</li>
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
        <#if organizationQo.parentId != 0>
            <a href="${rc.contextPath}/plugin/org?parentId=${returnId?c}"
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
            <th>机构号</th>
            <th>机构名</th>
            <th>简称</th>
            <th>类型</th>
            <th>地址</th>
            <th>备注</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.id?c}">
                </td>
                <td>
                    <a href="${rc.contextPath}/plugin/org?parentId=${item.id?c}">${item.code!?html}</a>
                </td>
                <td>${item.fullName!?html}</td>
                <td>${item.shortName!?html}</td>
                <td>${item.type!?html}</td>
                <td>${item.address!?html}</td>
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
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="parentId" value="${organizationQo.parentId?c}">
            <input type="hidden" name="id" value="${r'${id}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>编号 :</span>
                <div class="col-sm-16">
                    <input type="text" name="code" value="${r'${code}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>名称 :</span>
                <div class="col-sm-16">
                    <input type="text" name="fullName" value="${r'${fullName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6">简称 :</span>
                <div class="col-sm-16">
                    <input type="text" name="shortName" value="${r'${shortName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>地址 :</span>
                <div class="col-sm-16">
                    <textarea name="address" class="form-control" rows="2">${r'${address}'}</textarea>
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
