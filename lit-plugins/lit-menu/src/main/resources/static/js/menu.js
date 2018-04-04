var page = {
    topic: '菜单',
    restUrl: '/plugin/menu'
}

$(function () {

    var urlPrefix = contextPath + '/plugin/menu';

    var $dataResult = $('#data-result');


    var menuTreeConfig = {
        view: {
            selectedMulti: false
        }
    };

    /**
     * 移动菜单
     */
    var menuTree;

    function initMenuTree() {
        if (!menuTree) {
            Http.get(contextPath + '/plugin/menu/tree.json', function (res) {
                var menuData = {
                    name: '菜单',
                    children: res.data
                };
                menuTree = $.fn.zTree.init($('.ztree'), menuTreeConfig, menuData);
                menuTree.expandAll(true)
            })
        }
        return menuTree
    }

    $dataResult.find('.data-move').on('click', function (e) {
        initMenuTree()
        var id = getId(e);
        layer.open({
            type: 1,
            title: '移动',
            area: '280px',
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
                    if (id === checkNode.menuId) {
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
                Http.post(urlPrefix + '/move.json', data, function (result) {
                    if (result.success) {
                        UrlUtils.addParamAndReload('message', '移动菜单成功')
                    } else {
                        MsgUtils.error(result.message);
                    }
                })
            }
        })
    });

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

        Http.post(urlPrefix + (enable ? '/disable' : '/enable') + '.json', {
            menuId: getId(e)
        })
    });


});