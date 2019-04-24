<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='菜单管理'
importCss=['libs/zTree/3.5/css/metroStyle/metroStyle.css']
importJs=['libs/zTree/3.5/js/ztree.all.min.js', "js/menu.js"]>
<!-- 导航条 -->
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li class="active">系统管理</li>
            <li class="active">菜单管理</li>
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
        <#if menuQo.parentId != 0>
            <a href="${rc.contextPath}/plugin/menu?parentId=${returnId?c}"
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
            <!--<th>行号</th>-->
            <th>编码</th>
            <th>名称</th>
            <th>图标</th>
            <th>URL</th>
            <th>类型</th>
            <th>顺序号</th>
            <th>状态</th>
        <#--<th>备注</th>-->
            <th>操作</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.id?c}">
                </td>
            <#--<td text="${item?counter}"></td>-->
                <td>
                    <a href="${rc.contextPath}/plugin/menu?parentId=${item.id?c}">${item.code!?html}</a>
                </td>
                <td>${item.name!?html}</td>
                <td><i class="fa ${item.icon}"></i></td>
                <td>${item.url!?html}</td>
                <td>${item.type!?html}</td>
                <td>${item.orderNum?c}</td>
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
            <#--<td>${item.remark!?html}</td>-->
                <td>
                    <a class="data-move btn btn-xs btn-primary">
                        <i class="fa fa-random"></i>&nbsp;移动
                    </a>
                </td>
            </tr>
            <#else>
                <@AdminLayout.emptyData 8/>
            </#list>
        </tbody>
    </table>
</div>

<div id="menu-tree" class="modal-body text-center" style="display: none">
    <ul class="ztree"></ul>
</div>

<script type="text/template" id="edit-tpl">
    <div class="modal-body">
        <form id="form-edit" class="form-horizontal" action="">
            <input type="hidden" name="parentId" value="${menuQo.parentId?c}">
            <input type="hidden" name="id" value="${r'${id}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单编码 :</span>
                <div class="col-sm-16">
                    <input type="text" name="code" value="${r'${code}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单名称 :</span>
                <div class="col-sm-16">
                    <input type="text" name="name" value="${r'${name}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单图标 :</span>
                <div class="col-sm-16">
                    <input type="text" name="icon" value="${r'${icon}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6">菜单URL :</span>
                <div class="col-sm-16">
                    <input type="text" name="url" value="${r'${url}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6">顺序号 :</span>
                <div class="col-sm-16">
                    <input type="number" name="orderNum" value="${r'${orderNum}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>菜单类型 :</span>
                <div class="col-sm-16">
                    <select name="type" class="form-control">
                        <@dictTools dictKey='menu_type'>
                            <#list dictionaries as item>
                        <option value="${item.dictKey!}" {@if type==='${item.dictKey!}'}selected {@/if}>${item.dictValue!}</option>
                            </#list>
                        </@dictTools>
                    </select>
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
