$(function () {

    var compiledEditTpl = juicer($('#edit-tpl').html());

    var urlPrefix = path + '/plugin/role';

    var dataResult = $('#data-result');

    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        openEdit('增加角色', compiledEditTpl.render(), '#form-edit', urlPrefix + '/add.json')
    });

    /** 修改弹出框 */
    $('#data-update').on('click', function (e) {
        var checkedInputs = dataResult.find('.check-ls:checked');
        if (checkedInputs.length <= 0) {
            MsgUtils.warning('请选择一条数据 !');
            return;
        }
        if (checkedInputs.length > 1) {
            MsgUtils.warning('只能选择一条数据 !');
            return;
        }

        $.get(urlPrefix + '/get.json', {
            id: checkedInputs.val()
        }, function (result) {
            openEdit('修改角色', compiledEditTpl.render(result.result), '#form-edit', urlPrefix + '/update.json')
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


    var authTreeConfig = {
        data: {
            key: {
                name: "authorityName",
                title: "authorityName"
            }
        },
        check: {
            enable: true
        },
        callback: {
            onClick: function (e, treeId, treeNode) {
                var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
                zTreeObj.checkNode(treeNode, !treeNode.checked);
                zTreeObj.cancelSelectedNode();
            }
        }
    };

    var authTree;

    /** 分配权限 */
    $('.data-authorize').on('click', function (e) {
        if (!authTree) {
            initAuthTree();
        }
        authTree.checkAllNodes(false);
        var id = getId(e);
        layer.open({
            type: 1,
            title: '权限',
            area: "280px",
            offset: '20%',
            content: $('#authority-tree'),
            btn: ['确定', '取消'],
            success: function (dom, index) {
                $.post(path + '/plugin/authority/' + id + '/list.json', function (res) {
                    $.each(res.result, function (index, value) {
                        var node = authTree.getNodeByParam("authorityCode", value.authorityCode);
                        authTree.checkNode(node, true);
                    });
                });
            },
            btn1: function (index) {
                var checkedNodes = authTree.getCheckedNodes(true);
                // 构建请求参数
                var params = ['roleId=' + id];
                if (checkedNodes.length !== 0) {
                    $.each(checkedNodes, function (index, node) {
                        params.push('authorityIds=' + node.authorityId);
                    });
                }
                $.post(urlPrefix + '/bind/authority.json', params.join('&'), function (res) {
                    if (res.success) {
                        MsgUtils.success('分配权限成功!');
                    } else {
                        MsgUtils.error(res.message);
                    }
                });
            }
        });
    });

    function initAuthTree() {
        $.post(path + '/plugin/authority/tree.json', function (res) {
            var data = {
                authorityName: '权限',
                nocheck: true,
                children: res.result
            };
            authTree = $.fn.zTree.init($('.ztree'), authTreeConfig, data);
            authTree.expandAll(true);
        });
    }

    /** 删除功能 */
    $('#data-del').on('click', function (e) {
        var checkedInputs = dataResult.find('.check-ls:checked');
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
    })


});