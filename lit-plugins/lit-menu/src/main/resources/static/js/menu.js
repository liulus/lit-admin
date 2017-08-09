$(function () {

    var urlPrefix = path + "plugin/menu";

    var compiledEditTpl = juicer($('#edit-tpl').html());


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加', compiledEditTpl.render(), '#form-edit', urlPrefix + '/add.json')
    });

    /** 修改弹出框 */
    $('#data-update').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (!checkOnlyOne(checkedInputs.length)) {
            return;
        }

        $.post(path + 'plugin/menu/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改', compiledEditTpl.render(result['result']), '#form-edit', urlPrefix + '/update.json');
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
            $.post(urlPrefix + '/delete.json', checkedInputs.serialize(), function (result) {
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
            url: urlPrefix + "/list.json",
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

    /**
     * 移动菜单
     */
    var menuTree;
    $('#data-move').on('click', function (e) {

        var checkedInputs = $('.panel table .check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !');
            return;
        }

        if (!menuTree) {
            menuTree = $.fn.zTree.init($('.ztree'), menuTreeConfig, null);
        }

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
                    return;
                }

                var checkedMenuIds = checkedInputs.serialize();

                // 验证新的 父菜单 不是 被移动菜单本身或子菜单
                var checkNode = selectedNodes[0];
                var checked = true;
                while (checkNode) {
                    if (checkedMenuIds.indexOf(checkNode['menuId']) > 0) {
                        checked = false;
                        break;
                    }
                    checkNode = checkNode.getParentNode();
                }
                if (!checked) {
                    MsgUtils.warning('无法将菜单移动到此节点');
                    return;
                }

                var parentId = selectedNodes[0]['menuId'];
                var data = parentId ? 'parentId=' + parentId + '&' + checkedMenuIds : checkedMenuIds;
                $.post(urlPrefix + '/move.json', data, function (result) {
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
     * 向上移动菜单
     */
    $('#data-move-up').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (!checkOnlyOne(checkedInputs.length)) {
            return;
        }
        $.post(urlPrefix + '/move/up.json', {
            menuId: checkedInputs.val()
        }, function (result) {
            if (result['success']) {
                window.location.reload();
            } else {
                MsgUtils.error(result['message']);
            }
        })
    })

    /**
     * 向下移动菜单
     */
    $('#data-move-down').on('click', function (e) {
        var checkedInputs = $('.panel table .check-ls:checked');
        if (!checkOnlyOne(checkedInputs.length)) {
            return;
        }
        $.post(urlPrefix + '/move/down.json', {
            menuId: checkedInputs.val()
        }, function (result) {
            if (result['success']) {
                window.location.reload();
            } else {
                MsgUtils.error(result['message']);
            }
        })
    })

    function checkOnlyOne(checkedLength) {
        if (checkedLength <= 0) {
            MsgUtils.warning('请选择一条数据 !');
            return false;
        }
        if (checkedLength > 1) {
            MsgUtils.warning('只能选择一条数据 !');
            return false;
        }
        return true;
    }


    /**
     * 启用或禁用菜单
     */
    $('.data-enable, .data-disable').on('click', function (e) {
        var enable = $(this).hasClass('data-enable');
        if ($(this).hasClass('btn-default')) {
            return;
        }

        $(this).toggleClass('btn-default', true).toggleClass(enable ? 'btn-success' : 'btn-danger', false);
        $(this).find('span').toggleClass('invisible', true);

        var other = $(this).siblings();
        other.toggleClass('btn-default', false).toggleClass(enable ? 'btn-danger' : 'btn-success', true);
        other.find('span').toggleClass('invisible', false);

        $.post(urlPrefix + (enable ? '/disable' : '/enable') + '.json', {
            menuId: $(this).parents('tr').find('.check-ls').val()
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