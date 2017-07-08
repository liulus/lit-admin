$(function () {

    var $parent = $('.parent-menu');

    $parent.on('show.bs.collapse', function (e) {
        $(this).find('i').toggleClass('glyphicon-menu-left', false).toggleClass('glyphicon-menu-down', true);
    });

    $parent.on('hide.bs.collapse', function (e) {
        $(this).find('i').toggleClass('glyphicon-menu-left', true).toggleClass('glyphicon-menu-down', false);
    });




});