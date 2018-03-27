<#macro addBtn>
<button id="data-add" class="btn btn-sm btn-success">
    <i class="fa fa-plus"></i>&nbsp;&nbsp;新增
</button>
</#macro>

<#macro updateBtn>
<button id="data-update" class="btn btn-sm btn-info">
    <i class="fa fa-pencil"></i>&nbsp;&nbsp;修改
</button>
</#macro>

<#macro deleteBtn>
<button id="data-del" class="btn btn-sm btn-danger">
    <i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除
</button>
</#macro>
<#macro queryBtn>
<button id="data-query" class="btn btn-sm btn-primary">
    &nbsp;&nbsp;查询&nbsp;&nbsp;
</button>
</#macro>

<#macro emptyData col>
<tr>
    <td colspan="${col}">
        <div class="no-data text-center">
            <span><i class="fa fa-info-circle fa-1g"></i>&nbsp;&nbsp;没有数据</span>
        </div>
    </td>
</tr>
</#macro>

<#-- 分页条 -->
<#macro pagebar pageInfo pageUrl='' queryForm=''>
<div class="text-center pageContent" data-query-form="${queryForm}">
    <form action="${pageUrl}" method="get"><input type="hidden" name="pageNum"></form>
    <ul class="pagination pagination-sm">
        <#list pageInfo.start..pageInfo.end>
            <li>
                <#if pageInfo.pageNum == 1>
                    <span>第一页</span>
                <#else>
                    <a href="#" class="page-num" data-page-num="1">第一页</a>
                </#if>
            </li>
            <#items as index>
                <#if index == pageInfo.pageNum>
                    <li class="active"><span>${index?c}</span></li>
                <#else >
                    <li><a href="#" class="page-num" data-page-num="${index?c}">${index?c}</a></li>
                </#if>
            </#items>
            <li>
                <#if pageInfo.pageNum == pageInfo.totalPage>
                    <span>最后一页</span>
                <#else>
                    <a href="#" class="page-num" data-page-num="${pageInfo.totalPage?c}">最后一页</a>
                </#if>
            </li>
        </#list>
    </ul>
</div>
</#macro>