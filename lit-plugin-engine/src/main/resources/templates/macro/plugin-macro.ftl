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




