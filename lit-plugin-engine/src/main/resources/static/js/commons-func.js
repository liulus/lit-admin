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

    request: function (url, params, resCall, method) {

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

        $.ajax({
            url: url,
            type: method,
            data: params,
            success: resCall
        })
    },

    get: function (url, resCall) {
        this.request(url, null, resCall, 'get')
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







