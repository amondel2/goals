$(document).ready(function(){
 $("#saveBtn").bind('click',savebtn);
 $(".statusDropdownElm").bind('change',showDateDrop);
 $("#addBtn").bind('click',createNewCard);
 $("#YearChangeFrm select").bind('change',function(){
        $("#YearChangeFrm").submit();
     });
});

var createNewCard = function() {
    $.post(window.fmBaseDir + 'createCard',[] )
        .then(function(res) {
           var count = $('.card-body').length + 1;
            var div = $('<div class="card"><div class="card-header" role="tab" id="headingTwo'+count+'">\n' +
                '            <a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseTwo'+count+'" aria-expanded="false" aria-controls="collapseTwo'+count+'">\n' +
                '                <h5 class="mb-0 '+ res.id + '">New Goal ' + count  +'</h5>\n' +
                '            </a>\n' +
                '        </div>' +
                '</div>');

        var ul = $('<ul style="list-style: none;"></ul>');
        var li = $("<li></li>");
        li.append('<label for="'+ res.id + '_title">Goal Title: </label>');
        li.append('<input type="text" class="form-control" value="" minlength="3" name="'+ res.id + '_title" required="required" aria-required="true"  aria-label="title" />');
        $(ul).append(li);
        li = $("<li></li>");
        li.append('<label for="'+ res.id + '_descript">Goal Description: </label>');
        li.append('<textarea id="'+res.id+'_descript" name="'+res.id+'_descript" style="visibility: hidden; display: none;"></textarea>');
        li.append('<div id="'+ res.id + '_sdescript"></div>');
        $(ul).append(li);
        li = $("<li></li>");
        var divgrp = $('<div class="form-group"></div>');
        divgrp.append('<label for="'+ res.id + '_status">Goal Status: </label>');
        var item = $('#tmp_statusdrp').clone(true,true);
        item.attr('name',res.id + "_status");
        item.attr('id',res.id + "_status");
        divgrp.append(item);
        li.append(divgrp);
        $(ul).append(li);
        li = $("<li></li>");
        divgrp = $('<div class="form-group"></div>');
        divgrp.append('<label for="'+ res.id + '_types">Goal Status: </label>');
        item = $('#tmp_typesdrp').clone(true,true);
        item.attr('name',res.id + "_types");
        item.attr('id',res.id + "_types");
        divgrp.append(item);
        li.append(divgrp);
        $(ul).append(li);
        li = $("<li></li>");
        var divgrp1 = $('<div id="'+ res.id + '_targetDiv"></div>');
        divgrp = $('<div class="form-group"></div>');
        divgrp.append('<label for="'+ res.id + '_targetDate">Target Completed Date: </label>');
        divgrp.append('<input type="hidden" name="'+ res.id + '_targetDate" value="date.struct"/>');
        $.each($('#goalId_targetDiv select'),function (idx,value) {
            item = $(value).clone(true,true);
            item.attr('name',  res.id + "_" + $(item).prop('name'));
            item.attr('id',  res.id + "_" + $(item).prop('id'));
            divgrp.append(item);
        });
        divgrp1.append(divgrp);
        var divgrp2 = $('<div id="'+ res.id + '_completeDiv" style="display: none;"></div>');
        divgrp2.append('<label>Completed Date: </label> ' + $.trim($("#goalId_completeDiv").text()));
        li.append(divgrp1);
        li.append(divgrp2);
        $(ul).append(li);
        // <li>
            //                <div class="form-group">
            //                <label for="${goal.id}_status">Goal Status: </label>
            //                ${ps.goalStatusDropDown([value:goal.status,name:goal.id + "_status"])}
            //                </div>
            //            </li>

        var div1 = $('<div class="card-body"></div>').append(ul)
        var div2 = $('<div id="collapseTwo'+count+'" class="collapse" role="tabpanel" aria-labelledby="headingTwo'+count+'" data-parent="#accordionEx"></div>').append(div1);

        $(div).append(div2);
        $('#accordionEx').append(div);



        var instance = CKEDITOR.appendTo(res.id + '_sdescript',{ removeButtons:"Underline,JustifyCenter,Source,Flash,Image,Templates,Select,Iframe,HiddenField,TextField,Textarea,Checkbox,Button,ImageButton,Radio,Form"});

            CKEDITOR.on('instanceReady', function(){
                instance.updateElement();
            });

            instance.updateElement();

            instance.on( 'blur', function (e) {
                e.editor.updateElement();
                $("#" + res.id + "_descript").val(e.editor.getData());
            });

        }).fail(function () {
        alert("Fail");

    });

}

var showDateDrop = function() {

    var value = $(this).val();

    var baseSel = $(this).parent().parent().siblings().last().children();

    if ( value == 'Completed' ||value ==  'Cancelled' ) {
        $(baseSel).first().css('display','none');
        $(baseSel).last().css('display','block');
    } else {
        $(baseSel).last().css('display','none');
        $(baseSel).first().css('display','block');
    }

}


var savebtn = function() {
    var data = $("#gaolFrm").serialize();

    // var ids = [];

    $.each($(".card-body ul"),function(idx,val) {
        data =data + "&ids=" + $(val).children().first().children('input').prop('name');
    });

    data = data + "&empId=" + $("#emp_id").val();

    // data.ids = ids;
    $.post(window.fmBaseDir + 'saveGoals',data )
        .then(function(res) {
            $.each(res.titles,function(idx,value){
                $("h5." + idx).html(value);
            });
        }).fail(function () {
        alert("Fail");

    });
};

CKEDITOR.on('instanceReady', function(){
    $.each( CKEDITOR.instances, function(instance) {
        CKEDITOR.instances[instance].on("change", function(e) {
            for ( instance in CKEDITOR.instances )
                CKEDITOR.instances[instance].updateElement();
        });
    });
});