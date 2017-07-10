var compiledMsgTpl = juicer($('#alert-message-tpl').html());

var MsgUtils = {

    direction: {
        up: 1,
        down: 3,
        left: 4,
        right: 2
    },

    msg: function (messageType, message) {

        // 为了适应 bootstrap 提示框
        if (messageType === 'error') {
            messageType = 'danger'
        }

        var data = {
            messageType: messageType,
            message: message
        }

        layer.open({
            type: 1,
            area: '500px',
            minWidth: '500px',
            title: false,
            offset: 't',
            time: 2000,
            closeBtn: 0,
            shade: 0,
            content: compiledMsgTpl.render(data)
        })
    },

    success: function (message) {
        this.msg('success', message)
    },

    info: function (message) {
        this.msg('info', message)
    },

    warning: function (message) {
        this.msg('warning', message)
    },

    error: function (message) {
        this.msg('danger', message)
    },

    tip: function (message, dom, direction) {
        layer.tips(message, dom, {
            tips: [direction],
            time: 3000
        });
    }

}