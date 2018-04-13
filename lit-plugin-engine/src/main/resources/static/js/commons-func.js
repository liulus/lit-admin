$(function () {
    var defaultPage = {
        topic: '',
        restUrl: '',
        bindAdd: true,
        bindUpdate: true,
        bindDelete: true,
        editTpl: '#edit-tpl',
        editForm: '#edit-form'
    }

    var pageConfig = $.extend({}, defaultPage, page);

    var compiledEditTpl = juicer($(pageConfig.editTpl).html());

    var urlPrefix = contextPath + pageConfig.restUrl;

    var $dataResult = $('#data-result');

    if (pageConfig.bindAdd) {
        /** 新增弹出框 */
        $('#data-add').on('click', function (e) {
            var title = $.trim($(this).text()) + pageConfig.topic;
            layer.open({
                type: 1,
                title: title,
                area: '45%',
                content: compiledEditTpl.render(),
                btn: ['确认', '取消'],
                btn1: function (index) {
                    Http.post(urlPrefix + ".json", $('#form-edit').serialize(), function (res) {
                        if (res.success) {
                            UrlUtils.addParamAndReload('message', title + '成功')
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
    }

    if (pageConfig.bindUpdate) {
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
            var title = $.trim($(this).text()) + pageConfig.topic;
            Http.get(urlPrefix + '/' + checkedInputs.val() + '.json', function (res) {
                layer.open({
                    type: 1,
                    title: title,
                    area: '45%',
                    content: compiledEditTpl.render(res.data),
                    btn: ['确认', '取消'],
                    btn1: function (index) {
                        Http.put(urlPrefix + ".json", $('#form-edit').serialize(), function (res) {
                            if (res.success) {
                                UrlUtils.addParamAndReload('message', title + '成功')
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
    }

    if (pageConfig.bindDelete) {
        /** 删除功能 */
        $('#data-del').on('click', function (e) {
            var checkedInputs = $dataResult.find('.check-ls:checked');
            if (checkedInputs.length <= 0) {
                MsgUtils.warning('请至少选择一条数据 !');
                return;
            }
            var title = $.trim($(this).text()) + pageConfig.topic;
            layer.confirm('确定要删除选中的数据吗?', {icon: 3, title: title}, function (index) {
                Http.delete(urlPrefix + '.json', checkedInputs.serialize(), function (result) {
                    if (result.success) {
                        UrlUtils.addParamAndReload('message', title + '成功')
                    } else {
                        layer.close(index);
                        MsgUtils.error(result.message);
                    }
                });
            })
        })
    }

})


/**
 * 全选功能
 */
$('.check-all').on('click', function (e) {
    var checked = $(this).prop('checked');
    $(e.target).parents('table').find('.check-ls').prop('checked', checked);
});

/**
 * 获取点击当前行数据的 id
 * @param e 点击事件
 * @returns {*|jQuery}
 */
function getId(e) {
    return $(e.target).parents('tr').find('.check-ls').val()
}


var Http = {

    request: function (url, params, resCall, method, async) {

        if (method === 'delete') {
            var processed = url.indexOf('?') >= 0 ? params : '?' + params
            url = url + processed;
        }

        // fetch(url, {
        //     method: method,
        //     credentials: 'include',
        //     headers: {
        //         'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        //     },
        //     body: params
        // }).then(function (res) {
        //     return res.json()
        // }).then(resCall)

        var ajaxParam = {
            url: url,
            type: method,
            success: resCall
        }
        if (async) {
            ajaxParam.async = async
        }
        if (method !== 'delete') {
            ajaxParam.data = params
        }
        $.ajax(ajaxParam)
    },

    get: function (url, resCall, async) {
        this.request(url, null, resCall, 'get', async)
    },

    post: function (url, params, resCall) {
        this.request(url, params, resCall, 'post')
    },

    put: function (url, params, resCall) {
        this.request(url, params, resCall, 'put')
    },

    delete: function (url, params, resCall) {
        this.request(url, params, resCall, 'delete')
    }

}


function UrlUtils() {

}

UrlUtils.addParamAndReload = function (key, value, url) {
    var originUrl = url || window.location.href;
    var href = '';
    var param = {};
    if (originUrl.indexOf('?') >= 0) {
        var split = originUrl.split('?');
        href = split[0];
        var params = split[1].split('&');
        for (var i = 0; i < params.length; i++) {
            var kv = params[i].split('=');
            param[kv[0]] = kv[1];
        }
    } else {
        href = originUrl;
    }
    param[key] = value;

    var paramArray = [];
    for (var k in param) {
        paramArray.push(k + '=' + param[k]);
    }
    window.location.href = href + '?' + paramArray.join('&');
}







