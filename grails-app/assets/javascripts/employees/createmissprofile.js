/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $.each($(".btn-primary"), function (index, val) {
        $(val).on('click', function () {
            var that = $(this);
            $.ajax({
                url: redirectURL,
                data: {'uid': $(this).attr('id')},
                method: "GET",
                cache: false
            }).done(function (data) {
                if (data[0]) {
                    $(that).parent().remove();
                } else {
                    alert(data[1]);
                }
            });
        });
    });
});

