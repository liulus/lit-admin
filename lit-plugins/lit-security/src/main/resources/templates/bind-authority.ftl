<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='权限管理' importJs=['js/bind-authority.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li><a href="${rc.contextPath}/plugin/role">角色管理</a></li>
        <li class="active">分配权限</li>
    </ol>
</div>
<form id="bind-from" method="post">
    <input type="hidden" name="roleId" value="${RequestParameters['roleId']}">
    <div class="panel panel-default">

    <#list data as module>
        <div class="panel-heading">
            <label class="checkbox-inline module-check">
                <input type="checkbox" <#if module.checked>checked</#if>>${module.name!}
            </label>
        </div>
        <#if module.children??>
        <div class="panel-body module-panel">
            <#list module.children as function>
                <div class="col-sm-24 func-panel">
                    <div class="checkbox func-check">
                        <label><input type="checkbox" name="authorityIds" value="${function.id!}"
                                      <#if function.checked>checked</#if>>${function.name!}</label>
                    </div>
                </div>
                <#if function.children??>
                <div class="col-sm-offset-2 col-sm-22 item-panel">
                    <#list function.children as item>
                        <div class="col-sm-5 col-md-4">
                            <label class="checkbox-inline item-check">
                                <input type="checkbox" name="authorityIds" value="${item.id!}"
                                       <#if item.checked>checked</#if>>${item.name!}
                            </label>
                        </div>
                    </#list>
                </div>
                </#if>
            </#list>
        </div>
        </#if>
    </#list>
    </div>

    <div class="row text-center">
        <button class="btn btn-sm btn-primary">
            &nbsp;&nbsp;保存&nbsp;&nbsp;
        </button>
        <a href="${rc.contextPath}/plugin/role" class="btn btn-sm btn-default">
            &nbsp;&nbsp;取消&nbsp;&nbsp;
        </a>
    </div>
</form>
</@AdminLayout.listLayout>


