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



