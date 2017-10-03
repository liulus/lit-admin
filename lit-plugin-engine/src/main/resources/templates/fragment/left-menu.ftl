<div class="col-sm-6 col-md-4 sidebar">
<#list Application.menus>
    <ul class="nav nav-sidebar">
        <#items as menu>
            <#if menu.enable>
                <li class="${menu.isParent?string('parent-menu', '')}">
                    <a href="<#if menu.isParent>#sub-item-1-${menu?counter}<#else>${rc.contextPath}${menu.menuUrl!}</#if>"
                       data-toggle="collapse">
                        <#if menu.isParent>
                            <i class="fa fa-pencil">&nbsp;&nbsp;</i>${menu.menuName}
                            <i class="glyphicon glyphicon-menu-left pull-right"></i>
                        </#if>
                    </a>
                    <#if menu.isParent>
                        <ul id="sub-item-1-${menu?counter}" class="collapse children-menu">
                            <#list menu.children as second>
                                <#if second.enable>
                                    <li>
                                        <a href="${rc.contextPath}${second.menuUrl!}">
                                            <i class="fa fa-pencil">&nbsp;&nbsp;</i>${second.menuName!}
                                        </a>
                                    </li>
                                </#if>
                            </#list>
                        </ul>
                    </#if>
                </li>
            </#if>
        </#items>
    </ul>
</#list>
</div>