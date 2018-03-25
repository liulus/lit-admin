$(function () {

    var compiledEditTpl = juicer($('#edit-tpl').html());

    var urlPrefix = contextPath + '/plugin/dictionary';

    var $dataResult = $('#data-result');


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        layer.open({
            type: 1,
            title: '新增字典',
            area: '650px',
            content: compiledEditTpl.render(),
            btn: ['确认', '取消'],
            btn1: function (index) {
                Http.post(urlPrefix + ".json", $('#form-edit').serialize(), function (res) {
                    if (res.success) {
                        UrlUtils.addParamAndReload('message', '新增字典成功')
                    } else {
                        MsgUtils.error(res.message)
                    }
                })
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    });

    /** 修改弹出框 */
    $('#data-update').on('click', function (e) {
        var checkedInputs = $dataResult.find('.check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请选择一条数据 !');
            return;
        }
        if (checkedInputs.length > 1) {
            MsgUtils.warning('只能选择一条数据 !');
            return;
        }

        Http.get(urlPrefix + '/' + checkedInputs.val() + '.json', function (res) {
            layer.open({
                type: 1,
                title: '修改字典',
                area: '650px',
                content: compiledEditTpl.render(res.data),
                btn: ['确认', '取消'],
                btn1: function (index) {
                    Http.put(urlPrefix + ".json", $('#form-edit').serialize(), function (res) {
                        if (res.success) {
                            UrlUtils.addParamAndReload('message', '修改字典成功')
                        } else {
                            MsgUtils.error(res.message)
                        }
                    })
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        });
    });

    /** 删除功能 */
    $('#data-del').on('click', function (e) {
        var checkedInputs = $dataResult.find('.check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !');
            return;
        }
        layer.confirm('确定要删除选中的数据吗?', {icon: 3}, function (index) {
            Http.delete(urlPrefix + '/test.json', checkedInputs.serialize(), function (result) {
                if (result.success) {
                    UrlUtils.addParamAndReload('message', '删除字典成功')
                } else {
                    layer.close(index);
                    MsgUtils.error(result.message);
                }
            });
        })
    })

});