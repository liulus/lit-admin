<div class="col-sm-6 col-md-4 sidebar">
<#if Application.menus??>
    <#list Application.menus>
        <ul class="nav nav-sidebar">
            <#items as menu>
                <#if menu.enable>
                    <li class="${menu.isParent?string('parent-menu', '')}">
                        <a href="<#if menu.isParent>#sub-item-1-${menu?counter}<#else>${rc.contextPath}${menu.url!}</#if>"
                           data-toggle="collapse">
                            <#if menu.isParent>
                                <i class="fa fa-pencil">&nbsp;&nbsp;</i>${menu.name}
                                <i class="glyphicon glyphicon-menu-left pull-right"></i>
                            </#if>
                        </a>
                        <#if menu.isParent>
                            <ul id="sub-item-1-${menu?counter}" class="children-menu collapse in">
                                <#list menu.children as second>
                                    <#if second.enable>
                                        <li>
                                            <a href="${rc.contextPath}${second.url!}">
                                                <i class="fa fa-pencil">&nbsp;&nbsp;</i>${second.name!}
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
</#if>
</div>