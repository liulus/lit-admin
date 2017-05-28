$(function () {

    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        layer.open({
            type: 1,
            title: '增加',
            area: '650px',
            content: $('#modal-edit'),
            btn: ['确认', '取消'],
            btn1: function (index) {
                $.post(path + '/dictionary/add.json', $('#form-edit').serialize(), function (result) {
                    if (result['success']) {
                        $('#data-query').click();
                    } else {
                        layer.msg ( result[ 'message' ] );
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    });

    /** 新增弹出框 */
    $('.data-modify').on('click', function (e) {
        layer.open({
            type: 1,
            title: '编辑',
            area: '650px',
            content: $('#modal-edit'),
            btn: ['确认', '取消'],
            btn1: function (index) {
                $.post(path + '/dictionary/add.json', $('#form-edit').serialize(), function (result) {
                    if (result['success']) {
                        $('#data-query').click();
                    } else {
                        layer.msg ( result[ 'message' ] );
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    });



});