<script type="text/template" id="alert-message-tpl">
    <div id="msg-${r'${msgCount}'}" class="alert alert-${r'${msgType}'} alert-dismissible alert-message animated fadeIn"
         role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        {@if msgType === 'success'}
        <i class="fa fa-check"></i>
        {@else if msgType === 'info'}
        <i class="fa fa-info"></i>
        {@else if msgType === 'warning'}
        <i class="fa fa-exclamation"></i>
        {@else if msgType === 'danger'}
        <i class="fa fa-times"></i>
        {@/if}
        &nbsp;&nbsp;
    ${r'${message}'}
    </div>
</script>
<#--<script src="${rc.contextPath}/libs/fetch/2.0.3/fetch.js"></script>-->
<script src="${rc.contextPath}/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="${rc.contextPath}/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${rc.contextPath}/libs/juicer/0.6.8-stable/juicer-min.js"></script>
<script src="${rc.contextPath}/libs/layer/3.0.3/layer.js"></script>
<script src="${rc.contextPath}/js/left-menu.js"></script>
<script src="${rc.contextPath}/js/page.js"></script>
<script src="${rc.contextPath}/js/message.js"></script>
<script src="${rc.contextPath}/js/commons-func.js"></script>
