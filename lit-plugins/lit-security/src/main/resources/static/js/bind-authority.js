$(function () {

    $('#data-bind').on('click', function (e) {
        Http.post(contextPath + '/plugin/role/bind/authority.json', $('#bind-from').serialize(), function (res) {
            if (res.success) {
                window.location.href = contextPath + '/plugin/role';
            } else {
                MsgUtils.error(res.message)
            }
        })
    })


    $('.module-check').on('click', function (e) {
        var checked = $(this).find(':checkbox').prop('checked')
        $(this).parents('.panel-heading').next('.module-panel').find(':checkbox').prop('checked', checked)
    })

    $('.func-check').on('click', function (e) {
        // 设置子项 全选/取消全选
        var checked = $(this).find(':checkbox').prop('checked')
        $(this).parents('.func-panel').next('.item-panel').find(':checkbox').prop('checked', checked)

        // 子类全选时勾选父项, 子类不全选, 取消父项勾选
        var modulePanel = $(this).parents('.module-panel');
        if (checked) {
            var notChecked = modulePanel.find(':checkbox:not(:checked)');
            checked = notChecked.length === 0
        }
        modulePanel.prev('.panel-heading').find(':checkbox').prop('checked', checked)
    })

    $('.item-check').on('click', function (e) {
        var checked = $(this).find(':checkbox').prop('checked')

        var $funcCheckbox = $(this).parents('.item-panel').prev('.func-panel').find(':checkbox');

        var moduleChecked = false;
        if (checked) {
            var noCheckedItem = $(this).parents('.item-panel').find(':checkbox:not(:checked)');
            if (noCheckedItem.length === 0) {
                $funcCheckbox.prop('checked', checked)
                var noCheckModule = $(this).parents('.module-panel').find(':checkbox:not(:checked)');
                moduleChecked = noCheckModule.length === 0
            }
        } else {
            $funcCheckbox.prop('checked', checked)
        }

        $(this).parents('.module-panel').prev('.panel-heading').find(':checkbox').prop('checked', moduleChecked)
    })

})
