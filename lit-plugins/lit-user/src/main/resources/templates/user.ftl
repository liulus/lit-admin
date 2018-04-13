<#import "layout/list-layout.ftl" as AdminLayout>
<@AdminLayout.listLayout title='用户管理'importJs=['js/user.js']>
<!-- 导航条 -->
<div class="row">
    <ol class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li class="active">系统管理</li>
        <li class="active">用户管理</li>
    </ol>
</div>

<!-- 查询表单 -->
<div class="row">
    <form class="form-horizontal " action="" method="get" id="query-form">
        <div class="col-sm-12 col-md-10 form-group">
            <div class="col-sm-8 text-right control-label">关键字:</div>
            <div class="col-sm-16 ">
                <input name="keyword" class="form-control input-sm" type="text" value="${userQo.keyword!}"
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
            <th>用户名</th>
            <th>手机号</th>
            <th>性别</th>
            <th>锁定</th>
        </tr>
        </thead>

        <tbody>
            <#list data as item>
            <tr>
                <td class="text-center">
                    <input class="check-ls" name="ids" type="checkbox" value="${item.id?c}">
                </td>
                <td>${item?counter}</td>
                <td>${item.userName?html}</td>
                <td>${item.mobileNum!?html}</td>
                <td>${item.gender?string('男', '女')}</td>
                <td>${item.lock?string('是', '否')}</td>
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
            <input type="hidden" name="id" value="${r'${id}'}">
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>编号 :</span>
                <div class="col-sm-16">
                    <input type="text" name="code" value="${r'${code}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>用户名 :</span>
                <div class="col-sm-16">
                    <input type="text" name="userName" value="${r'${userName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>手机号 :</span>
                <div class="col-sm-16">
                    <input type="text" name="mobileNum" value="${r'${mobileNum}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>性别 :</span>
                <div class="col-sm-16">
                    <select name="gender" class="form-control">
                        <option value="true" {@if gender} selected {@/if}>男 </option>
                        <option value="false" {@if !gender} selected {@/if}>女 </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>电话 :</span>
                <div class="col-sm-16">
                    <input type="text" name="telephone" value="${r'${telephone}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>邮箱 :</span>
                <div class="col-sm-16">
                    <input type="text" name="email" value="${r'${email}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>真实名 :</span>
                <div class="col-sm-16">
                    <input type="text" name="realName" value="${r'${realName}'}" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <span class="control-label col-sm-6"><i class="text-danger">*&nbsp;</i>身份证 :</span>
                <div class="col-sm-16">
                    <input type="text" name="idCardNum" value="${r'${idCardNum}'}" class="form-control">
                </div>
            </div>
        </form>
    </div>
</script>

</@AdminLayout.listLayout>
