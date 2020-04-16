var page = {
    topic: '角色',
    restUrl: '/plugin/role'
}

$(function () {

    var authTreeConfig = {
        check: {
            enable: true
        },
        callback: {
            onClick: function (e, treeId, treeNode) {
                var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
                // 点击勾选
                zTreeObj.checkNode(treeNode, !treeNode.checked);
                zTreeObj.cancelSelectedNode(); // 取消所有选中状态
            }
        }
    };

    var authTree;

    function initAuthTree() {
        if (!authTree) {
            Http.get(contextPath + '/plugin/authority/tree.json', function (res) {
                var authData = {
                    name: '权限',
                    nocheck: true,
                    children: res.data
                };
                authTree = $.fn.zTree.init($('#authority-tree'), authTreeConfig, authData);
                authTree.expandAll(true)
            }, false)
        }
        return authTree
    }

    /** 分配权限 */
    $('.data-authorize').on('click', function (e) {
        if (!authTree) {
            initAuthTree();
        }
        authTree.checkAllNodes(false);
        var roleId = getId(e);
        layer.open({
            type: 1,
            title: '分配权限',
            area: "280px",
            offset: '20%',
            content: $('#authority-tree'),
            btn: ['确定', '取消'],
            success: function (dom, index) {
                Http.get(contextPath + '/plugin/role.json?roleId=' + roleId, function (res) {
                    $.each(res.data, function (index, value) {
                        var node = authTree.getNodeByParam('code', value.code)
                        if (node) {
                            authTree.checkNode(node, true)
                        }
                    })
                })
            },
            btn1: function (index) {
                var checkedNodes = authTree.getCheckedNodes(true);
                // 构建请求参数
                var params = ['roleId=' + roleId];
                if (checkedNodes.length !== 0) {
                    $.each(checkedNodes, function (index, node) {
                        params.push('authorityIds=' + node.id);
                    });
                }
                Http.post(contextPath + '/plugin/role/bind/authority.json', params.join('&'), function (res) {
                    if (res.success) {
                        UrlUtils.addParamAndReload('message', '分配权限成功')
                    } else {
                        MsgUtils.error(res.message);
                    }
                });
                layer.close(index);
            }
        });
    });

});