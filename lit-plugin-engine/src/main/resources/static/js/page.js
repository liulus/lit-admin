$(document).on('click', '.pagination .page-num', function (e) {
    var pageContent = $(e.target).parents('.pageContent');
    var pageNum = $(e.target).data('pageNum');

    var queryForm;
    if (pageContent.data('queryForm')) {
        queryForm = $(pageContent.data('queryForm'));
    }
    if (queryForm.length === 0) {
        queryForm = pageContent.find('form');
    }

    var pageNumInput = queryForm.find('input[name="pageNum"]');
    if (pageNumInput.length >= 1) {
        pageNumInput.val(pageNum);
    } else {
        queryForm.append('<input type="hidden" name="pageNum" value="' + pageNum + '">');
    }

    queryForm.submit();

});