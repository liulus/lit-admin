var page = {
    topic: '菜单',
    restUrl: '/plugin/menu'
}

$(function () {

    var urlPrefix = contextPath + '/plugin/menu';

    var $dataResult = $('#data-result');


    var menuTreeConfig = {
        async: {
            url: urlPrefix + '.json',
            dataType: 'json',
            type: 'get',
            enable: true,
            autoParam: ['menuId=parentId'],
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
                name: 'menuName',
                title: 'menuName'
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