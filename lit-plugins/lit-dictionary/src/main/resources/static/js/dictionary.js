$(function () {

    var compiledEditTpl = juicer($('#edit-tpl').html());

    var urlPrefix = path + 'plugin/dictionary';


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加', compiledEditTpl.render(), '#form-edit', urlPrefix + '/add.json')
    });

    /** 修改弹出框 */
    $('#data-update').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请选择一条数据 !')
            return;
        }
        if (checkedInputs.length > 1) {
            MsgUtils.warning('只能选择一条数据 !')
            return;
        }

        $.post(urlPrefix + '/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改', compiledEditTpl.render(result['result']), '#form-edit', urlPrefix + '/update.json')
        });

    })

    function openEdit(title, content, form, url) {
        layer.open({
            type: 1,
            title: title,
            area: '650px',
            content: content,
            btn: ['确认', '取消'],
            btn1: function (index) {
                $.post(url, $(form).serialize(), function (result) {
                    if (result['success']) {
                        $('#query-form').submit();
                    } else {
                        MsgUtils.error(result['message']);
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    }

    $('#data-del').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !')
            return;
        }
        layer.confirm('确定要删除选中的数据吗?', function (index) {
            $.post(urlPrefix + '/delete.json', checkedInputs.serialize(), function (result) {
                if (result['success']) {
                    $('#query-form').submit();
                } else {
                    layer.close(index);
                    MsgUtils.error(result['message']);
                }
            });
        })
    })

    /**
     * 全选功能
     */
    $('.check-all').on('click', function (e) {
        var checked = $(this).prop('checked');
        $(this).parents('table').find('.check-ls').prop('checked', checked);
    })

})