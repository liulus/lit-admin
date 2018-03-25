var compiledMsgTpl = juicer($('#alert-message-tpl').html());

var msgCount = 1;

var MsgUtils = {

    direction: {
        up: 1,
        down: 3,
        left: 4,
        right: 2
    },

    msg: function (messageType, message) {

        $('.alert-message').alert('close')

        // 为了适应 bootstrap 提示框
        if (messageType === 'error') {
            messageType = 'danger'
        }

        var data = {
            msgCount: msgCount,
            msgType: messageType,
            message: message
        };

        $('body').append(compiledMsgTpl.render(data))
        var id = '#msg-' + msgCount;
        setTimeout(function () {
            $(id).alert('close')
        }, 4000);

        msgCount++;
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
if (message.content) {
    MsgUtils.success(message.content);
}
