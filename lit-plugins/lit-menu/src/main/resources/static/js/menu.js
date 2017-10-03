$(function () {

    var urlPrefix = path + "/plugin/menu";

    var compiledEditTpl = juicer($('#edit-tpl').html());

    var $dataResult = $('#data-result');


    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加菜单', compiledEditTpl.render(), '#form-edit', urlPrefix + '/add.json')
    });

    /** 修改弹出框 */
    $('#data-update').on('click', function (e) {
        var checkedInputs = $dataResult.find('.check-ls:checked');
        if (!checkOnlyOne(checkedInputs.length)) {
            return;
        }

        $.post(urlPrefix + '/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改菜单', compiledEditTpl.render(result.result), '#form-edit', urlPrefix + '/update.json');
        });

    });

    function openEdit(title, content, form, url) {
        layer.open({
            type: 1,
            title: title,
            area: '650px',
            content: content,
            btn: ['确认', '取消'],
            btn1: function (index) {
                $.post(url, $(form).serialize(), function (result) {
                    if (result.success) {
                        window.location.reload();
                    } else {
                        MsgUtils.error(result.message);
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
        var checkedInputs = $dataResult.find('.check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请至少选择一条数据 !');
            return;
        }
        layer.confirm('确定要删除选中的数据吗?', {icon: 3}, function (index) {
            $.post(urlPrefix + '/delete.json', checkedInputs.serialize(), function (result) {
                if (result.success) {
                    window.location.reload();
                } else {
                    layer.close(index);
                    MsgUtils.error(result.message);
                }
            });
        })
    });


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
                };
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
    };


    /**
     * 移动菜单
     */
    var menuTree = $.fn.zTree.init($('.ztree'), menuTreeConfig, null);

    $dataResult.find('.data-move').on('click', function (e) {

        var id = getId(e);
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

                // 验证新的 父菜单 不是 被移动菜单本身或子菜单
                var checkNode = selectedNodes[0];
                var checked = true;
                while (checkNode) {
                    if (id.indexOf(checkNode.menuId) >= 0) {
                        checked = false;
                        break;
                    }
                    checkNode = checkNode.getParentNode();
                }
                if (!checked) {
                    MsgUtils.error('无法将菜单移动到此节点');
                    return;
                }

                var parentId = selectedNodes[0].menuId;
                var data = parentId ? 'parentId=' + parentId + '&ids=' + id : 'ids=' + id;
                $.post(urlPrefix + '/move.json', data, function (result) {
                    if (result.success) {
                        window.location.reload();
                    } else {
                        MsgUtils.error(result.message);
                    }
                })
            }
        })
    });

    /**
     * 向上移动菜单
     */
    $dataResult.find('.data-move-up').on('click', function (e) {
        $.post(urlPrefix + '/move/up.json', {
            menuId: getId(e)
        }, function (result) {
            if (result.success) {
                window.location.reload();
            } else {
                MsgUtils.error(result.message);
            }
        })
    });

    /**
     * 向下移动菜单
     */
    $dataResult.find('.data-move-down').on('click', function (e) {
        $.post(urlPrefix + '/move/down.json', {
            menuId: getId(e)
        }, function (result) {
            if (result.success) {
                window.location.reload();
            } else {
                MsgUtils.error(result.message);
            }
        })
    });

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
    $dataResult.find('.data-enable, .data-disable').on('click', function (e) {
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
            menuId: getId(e)
        })
    });


});