$(function () {


    $('#data-test').on('click', function (e) {
        // MsgUtils.info('this is a info');

        layer.tips('hisss', this, {
            tips: [1]
        })

    })


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加', compiledEditTpl.render(), '#form-edit', 'dictionary/add.json')
    });

    /** 修改弹出框 */
    $('#data-modify').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            layer.msg('请选择一条数据 !')
            return;
        }
        if (checkedInputs.length > 1) {
            layer.msg('只能选择一条数据 !')
            return;
        }

        $.post(path + 'dictionary/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改', compiledEditTpl.render(result['result']), '#form-edit', 'dictionary/update.json')
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
                $.post(path + url, $(form).serialize(), function (result) {
                    if (result['success']) {
                        $('#query-form').submit();
                    } else {
                        layer.msg(result['message']);
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
            layer.msg('请至少选择一条数据 !')
            return;
        }
        layer.confirm('确定要删除选中的数据吗?', function () {
            $.post(path + 'dictionary/delete.json', checkedInputs.serialize(), function (result) {
                if (result['success']) {
                    $('#query-form').submit();
                } else {
                    layer.msg(result['message']);
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