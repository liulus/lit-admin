var page = {
    topic: '用户',
    restUrl: '/plugin/user'
}

$(function () {

    var roleTreeConfig = {
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

    var roleTree;

    function initRoleTree() {
        if (!roleTree) {
            Http.get(contextPath + '/plugin/role.json?pageSize=50', function (res) {
                var roleData = {
                    name: '角色',
                    nocheck: true,
                    children: res.data
                };
                roleTree = $.fn.zTree.init($('#role-tree').find('.ztree'), roleTreeConfig, roleData);
                roleTree.expandAll(true)
            }, false)
        }
        return roleTree
    }

    /** 分配权限 */
    $('.data-authorize').on('click', function (e) {
        if (!roleTree) {
            initRoleTree();
        }
        var userId = checkOneAndGetValue($('#data-result'));
        if (!userId) {
            return
        }
        roleTree.checkAllNodes(false);
        layer.open({
            type: 1,
            title: '分配角色',
            area: "280px",
            offset: '20%',
            content: $('#role-tree'),
            btn: ['确定', '取消'],
            success: function (dom, index) {
                Http.get(contextPath + '/plugin/role.json?pageSize=50&userId=' + userId, function (res) {
                    $.each(res.data, function (index, value) {
                        var node = roleTree.getNodeByParam('code', value.code)
                        if (node) {
                            roleTree.checkNode(node, true)
                        }
                    })
                })
            },
            btn1: function (index) {
                var checkedNodes = roleTree.getCheckedNodes(true);
                // 构建请求参数
                var params = ['userId=' + userId];
                if (checkedNodes.length !== 0) {
                    $.each(checkedNodes, function (index, node) {
                        params.push('roleIds=' + node.id);
                    });
                }
                Http.post(contextPath + '/plugin/role/bind/user.json', params.join('&'), function (res) {
                    if (res.success) {
                        UrlUtils.addParamAndReload('message', '分配角色成功')
                    } else {
                        MsgUtils.error(res.message);
                    }
                });
                layer.close(index);
            }
        });
    });

});