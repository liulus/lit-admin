$(function () {

    var compiledEditTpl = juicer($('#edit-tpl').html());


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加', compiledEditTpl.render(), '#form-edit', 'plugin/menu/add.json')
    });

    /** 修改弹出框 */
    $('#data-modify').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请选择一条数据 !');
            return;
        }
        if (checkedInputs.length > 1) {
            MsgUtils.warning('只能选择一条数据 !');
            return;
        }

        $.post(path + 'plugin/menu/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改', compiledEditTpl.render(result['result']), '#form-edit', 'plugin/menu/update.json');
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
                        window.location.reload();
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

    /**
     * 删除功能
     */
    $('#data-del').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !');
            return;
        }
        layer.confirm('确定要删除选中的数据吗?', function () {
            $.post(path + 'plugin/menu/delete.json', checkedInputs.serialize(), function (result) {
                if (result['success']) {
                    window.location.reload();
                } else {
                    MsgUtils.error(result['message']);
                }
            });
        })
    })


    var menuTreeConfig = {
        async: {
            url: path + "plugin/menu.json",
            dataType: "json",
            enable: true,
            autoParam: ["menuId=parentId"],
            dataFilter: function (treeId, parentNode, responseData) {
                if (parentNode) {
                    return responseData.result;
                }

                var data = {
                    menuName: '菜单',
                    children: responseData.result
                }
                return data;
            }
        },
        data: {
            key: {
                name: "menuName",
                title: "menuName"
            }
        },
        view: {
            selectedMulti: false
        }
    }

    var menuTree;
    $('#data-move').on('click', function (e) {

        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !');
            return;
        }

        if (menuTree === undefined) {
            menuTree = $.fn.zTree.init($('.ztree'), menuTreeConfig, null);
        }

        var serialize = checkedInputs.serialize();
        var nodesByFilter = menuTree.getNodesByFilter(function (node) {
            return serialize.indexOf('ids=' + node['menuId']) >= 0;
        }, false);
        menuTree.hideNodes(nodesByFilter);


        layer.open({
            type: 1,
            title: '菜单',
            area: "400px",
            offset: '20%',
            content: $('#menu-tree'),
            btn: ['确定', '取消'],
            btn1: function (index) {
                var selectedNodes = menuTree.getSelectedNodes();
                if (selectedNodes.length === 0) {
                    MsgUtils.warning('请选择一个节点');
                }
                var parentId = selectedNodes[0]['menuId'];
                var data = parentId ? 'parentId=' + parentId + '&' + serialize : serialize;
                $.post(path + 'plugin/menu/move.json', data, function (result) {
                    if (result['success']) {
                        window.location.reload();
                    } else {
                        MsgUtils.error(result['message']);
                    }
                })
            }
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