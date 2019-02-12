window.rootNodeId = 'root';
var intialPersonDropDowns = 5;
var dropdownlist = ['portfolio','projects','subproj','jobs','jobSubFunction'];
var workDropdownCount = 0;
var currentsmonth = 0;
var currenendmonth = 11;

var cantEdit = [];

$(function() {
    initialStyling();
    setFormSubmitHandler();
    setFormInputHandler();
     createJSTree();
     $("#myDate_year").on('change',function(){
         $("#YearChangeFrm").submit();
     })
});

$(document).on('click', function() {
    var formInput = $('.fm-form-input');

    removeHeaderMsg();
    formInput.removeClass('fm-error');
    formInput.siblings('.fm-validation-msg').text('');
});

function initialStyling() {
    $('input[type="button"], input[type="submit"]').button();
}

function createJSTree() {
    var jstree = $('#jstree');

    var plugins = ['wholerow', 'sort', 'dnd']

    jstree.jstree({
        plugins: plugins,
        core: {
            check_callback: function(operation, node, parentNode, nodePosition, more) {
                if (more && more.dnd && operation === 'move_node') {
                    return isDroppable(parentNode, more);
                }

                return true;
            },
            strings: {
                'Loading ...': window.fmLoadingMsg
            },
            multiple: false,
            force_text: true,
            themes: {
                icons: false
            },
            data: function(obj, callback) {
                if (this._cnt === 0) {
                    getInitialData(this, callback);
                } else {
                    getPortfolioChildren(this, obj, callback);
                }
            }
        },
        sort: function(firstNodeId, secondNodeId) {
            var firstNode = this.get_node(firstNodeId).text;
            var secondNode = this.get_node(secondNodeId).text;

            var status = firstNode.localeCompare(secondNode, undefined, {
                numeric: true,
                sensitivity: 'base'
            });

            return status >= 0 ? 1 : -1;
        },
        dnd: {
            large_drop_target: true,
            large_drag_target: true,
            touch: true,
            is_draggable: function(data) {
                if (data[0].id === 'root') {
                    return false;
                }

                return true;
            }
        }
    });

    jstree.on('select_node.jstree', function(e, data) {
        var portfolioData = data.node.data;
        setJSTreeSelectionLogic(portfolioData);
    });

    jstree.on('move_node.jstree', handleDragAndDrop);

    jstree.on('loaded.jstree refresh.jstree', function() {
        toggleExportButtonVisibility();
        $('#' + window.rootNodeId + ' > div:first-child').click();
    });
}

function getInitialData(that, callback) {
    $.get(window.fmBaseDir + 'getInitialData',{year:workingYear,bossId:fmBossId}).then(function(data) {
        var startingTree = getInitialTree(data);
        $.each(data.childData,function(index,val) {
           if(!val.canedit) {
               cantEdit.push(val.id)
           }
        });
        callback.call(that, startingTree);
    }).fail(displayErrorMsg);
}

function getInitialTree(data) {
    return [
        {
            id: window.rootNodeId,
            text: window.fmCompanyName,
            state: {
                opened: true,
                selected: true
            },
            children: data.childData,
            data: {
                id: window.rootNodeId
            }
        }
    ];
}

function getPortfolioChildren(that, obj, callback) {
    $.get(window.fmBaseDir + 'loadEmployeeChildren', {
        id: obj.id,
        year: workingYear
    }).then(function(data) {
        callback.call(that, data);
    }).fail(displayErrorMsg);
}

function setJSTreeSelectionLogic(portfolioData) {
    if (portfolioData.id === window.rootNodeId) {
        $('.fm-field').text('');
        $('.fm-only-on-select').hide();
        $('.fm-right-header > div:not(:nth-child(3))').hide();
    } else {
        setHeaderInfo(portfolioData);
        $('.fm-right-header > div').css('display', 'flex');
        $('.fm-only-on-select').show();
    }

    $('.fm-right-form').hide();
    removeSelectedBtnClassFromAll()
}

function setHeaderInfo(data) {
    var name = data.name,
        employeeId = data.goalStatus;

    $('#fm-name-field').text(name);
    $('#fm-portfolio-id-field').text(employeeId);
}

function setFormInfo() {
    var name = $('#fm-name-field').text(),
        employeeId = $('#fm-portfolio-id-field').text();

    $('#fm-name-input').val(name);
    $('#fm-employeeId-input').val(employeeId);
}

function createChildBtnHandler(el) {
    setSelectedBtn(el);
    $('.fm-right-form input[type="text"]').val('');
    $('#fm-portfolioUnitId-form-field').show();
    $('.fm-right-form').css('display', 'flex');
}

function updateBtnHandler(el) {
    setSelectedBtn(el);
    setFormInfo();
    $('#fm-portfolioUnitId-form-field').hide();
    $('.fm-right-form').css('display', 'flex');
}

function deleteBtnHandler() {
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    $('#fm-delete-confirm').dialog({
        modal: true,
        width: 550,
        resizable: false,
        buttons: [
            {
                text: window.fmConfirmMsg,
                click: function() {
                    deletePortfolio();
                    $(this).dialog('close');
                }
            },
            {
                text: window.fmCancelMsg,
                click: function() {
                    $(this).dialog('close');
                }
            }
        ]
    });
}


function copyEditDropDowns(disableDropDowns,startmonth,endmonth){
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var tr = $("<tr></tr>");
    $.each($("#dropdowns").children(),function(index,val){
        var item = $(val).clone(true,true);
        if(disableDropDowns) {
            $(item).prop('disabled',true);
    }
        var td = $("<td></td>");
        $(item).attr('name',workDropdownCount + "_" + $(item).attr('name') + currentNodeId);
        $(item).appendTo(td);
        $(td).appendTo(tr);
    });
    $.each($("#tableItems").children(),function(index,val){
        var item = $(val).clone(true,true);
        var td = $("<td></td>");
        $(item).attr('name',workDropdownCount + "_" + $(item).attr('name') + "_" + currentNodeId);
        if(index < startmonth || index  > endmonth ) {
            $(item).prop('disabled',true);
        }
        $(item).appendTo(td);
        $(td).appendTo(tr);
    });
    var td = $("<td></td>");
    var item = $("<button name='delete' onclick='deleteRow("+workDropdownCount + ",\""+currentNodeId+"\");'>Delete</button>");
    $(item).appendTo(td);
    $(td).appendTo(tr);
    $(tr).appendTo('#fm-editpersongoals table');
}

function copyGoalStatusDropDowns(title,status){
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var tr = $("<tr id='" + workDropdownCount + '_' + currentNodeId+"'></tr>");
    $("<td><input type='text' name='"+workDropdownCount+"_title_" + currentNodeId + "' value='" + title + "'></td>").appendTo(tr);
    var td = $("<td></td>");
        var item = $("#gs_select").children().first().clone(true,true);
        $(item).attr('name',workDropdownCount + "_" + $(item).attr('name') + "_" + currentNodeId);
        $(item).attr('id', workDropdownCount + "_goalStatusSelect_" + currentNodeId);
        $(item).appendTo(td);
        $(td).appendTo(tr);

    var td1 = $("<td></td>");
    var itemdel = $("<button name='delete' onclick='deleteRow("+workDropdownCount + ",\""+currentNodeId+"\");'>Delete</button>");
    $(itemdel).appendTo(td1);
    $(td1).appendTo(tr);
    $(tr).appendTo('#fm-editpersongoals table');
    $("#" + workDropdownCount + "_goalStatusSelect_" + currentNodeId).val(status);
}

function populateDropDown(counter,workDropdownCount,currentNodeId,resultSet) {
    if(counter >= dropdownlist.length) {
        return true;
    } else {
        var value = dropdownlist[counter];
        if( resultSet[value] ) {
            $('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').find('option:selected').removeAttr('selected');
            $('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').find('option[value="' + resultSet[value] + '"]').attr("selected", true);
            $.when($('select[name="' + workDropdownCount + '_' + value + '_' + currentNodeId + '"]').trigger('change')).then(function () {
                populateDropDown(++counter, workDropdownCount, currentNodeId, resultSet);
            });
        } else {
            populateDropDown(++counter, workDropdownCount, currentNodeId, resultSet);
        }
    }
}

function markLeave() {
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    if(cantEdit.indexOf(currentNodeId[0]) >= 0 ) {
        alert("NO ACCESS TO EDIT THIS PERSON");
        return;
    }
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    $('#fm-markleave').dialog({
        modal: true,
        minWidth: 800,
        minHeight: 250,
        maxHeight: 600,
        resizable: true,
        open: function( event, ui ) {
            var jstree = $('#jstree').jstree(true);
            var currentNodeId = jstree.get_selected();
            $("#leaveEmpId").val(currentNodeId[0]);
            $('#fm-markleave').dialog( "option", "title", "Mark Exit for " + $.trim(jstree.get_selected(true)[0]['text']));
        },
        buttons: [
            {
                text: 'close',
                click: function() {
                    $(this).dialog('close');
                }
            },
            {
                text: 'save',
                click: function () {
                        var data = {};
                        var that = this;

                        data['leaveDate'] = $("#leaveDate_month").val() + "/" + $("#leaveDate_day").val() + "/" + $("#leaveDate_year").val();
                        data['empId'] =$("#leaveEmpId").val();
                        $.post(window.fmBaseDir + 'saveLeaveDate', data)
                            .then(function(res) {
                                if(res.rs == "Success") {
                                    displaySuccessMsg()
                                }
                                $(that).dialog('close');
                            });
                }
            }
    ]
    });
}

function editpersongoal() {
    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    if(cantEdit.indexOf(currentNodeId[0]) >= 0 ) {
        alert("NO ACCESS TO EDIT THIS PERSON");
        return;
    }
    window.location.href = window.fmGoalsDir + 'index?id='+ currentNodeId + '&year=' + workingYear;
    removeSelectedBtnClassFromAll();
    $('.fm-right-form').hide();

    // $('#fm-editpersongoals').dialog({
    //     modal: true,
    //     minWidth: 800,
    //     minHeight: 250,
    //     maxHeight: 1000,
    //     resizable: true,
    //
    //     open: function( event, ui ) {
    //         var jstree = $('#jstree').jstree(true);
    //         var currentNodeId = jstree.get_selected();
    //         workDropdownCount = 0;
    //         $('#editpersongoalstable tr:not(:first)').remove();
    //
    //         var data = {};
    //         data['empId'] = currentNodeId[0];
    //         data['year'] = workingYear;
    //         // window.location.href = window.fmGoalsDir + 'index?id='+ currentNodeId + '&year=' + workingYear;
    //         $.post(window.fmBaseDir + 'getResultSet', data)
    //             .then(function(res) {
    //                 if(res.rs && res.rs.length > 0 ) {
    //                     var x = 0;
    //                     for(x;x < res.rs.length;x++){
    //                         workDropdownCount++;
    //                         copyGoalStatusDropDowns(res.rs[x].title,res.rs[x].status.name);
    //                     }
    //                 }
    //             }).fail(displayErrorMsg);
    //         $('#fm-editpersongoals').dialog( "option", "title", $.trim(jstree.get_selected(true)[0]['text']));
    //     },
    //     buttons: [
    //         {
    //             text: 'close',
    //             click: function() {
    //                 $(this).dialog('close');
    //             }
    //         },
    //         {
    //           text: "view",
    //             click: function() {
    //                 var currentNodeId = jstree.get_selected();
    //                 window.location.href = window.fmGoalsDir + 'index?id='+ currentNodeId + '&year=' + workingYear;
    //             }
    //         },
    //         {
    //             text: 'add',
    //             click: function() {
    //                 ++workDropdownCount;
    //                 copyGoalStatusDropDowns("","");
    //             }
    //         },
    //         {
    //             text: 'save',
    //             click: function(){
    //                 var data = {};
    //                 var selectBoxes = {};
    //                 var hashBoxes = {};
    //                 var responses = {};
    //                 var i = 1;
    //                 var that = this;
    //                 var warn = false;
    //                 var valid = true;
    //                 var warnMsg = "";
    //                 var errorMsg = "";
    //                 var countPercentBox = [];
    //                 $("#fm-editpersongoals table tr td").css('backgroundColor','white');
    //                 for(i;i<=workDropdownCount;i++) {
    //                     var selectBox = {};
    //                     var hashBox = "";
    //                     var val = $("#editpersongoalstable tr td select[name^='" + i + "_']");
    //                     var text = $.trim(val.find("option:selected").text())
    //                         if (!text || text.length === 0 ) {
    //                             valid = false;
    //                             var elm = $(val).parent().parent();
    //                             errorMsg +=  "Row " +  ($("#editpersongoalstable tbody tr").index(elm)  + 1) + " is not completly filled out\n\r";
    //                             $(elm).find('td').css('backgroundColor','red');
    //                             return false;
    //                         }
    //                     selectBox[$(val).attr('name')] = text;
    //
    //
    //
    //                     $("#fm-editpersongoals table tr td input[name^='" + i + "_']");
    //                     responseBox[$(td).attr('name')] = $(td).val();
    //                         countPercentBox[index] = parseFloat(countPercentBox[index] ? countPercentBox[index] : 0)  + parseFloat($(td).val() ? $(td).val() : 0);
    //                     responses[i] = responseBox;
    //                     selectBoxes[i] = selectBox;
    //                 }
    //                 i=0;
    //
    //                 for(i;i<12;i++) {
    //                     if(countPercentBox[i] > 100 ) {
    //                         valid = false;
    //                         var count = $("#fm-editpersongoals table tr").last().find('td select').length;
    //                         errorMsg += "Column " + (i + count + 1) + " is over 100\r\n";
    //                         $.each($("#fm-editpersongoals table tr td input").parent().parent(),function(index, val){
    //
    //                             $($(val).find('td input').get(i)).parent().css('backgroundColor','red');
    //                         });
    //                     } else if (countPercentBox[i] > 0 && countPercentBox[i] < 100  ) {
    //                         warn = true;
    //                         var count = $("#fm-editpersongoals table tr").last().find('td select').length;
    //                         warnMsg += "Column " + (i + count + 1) + " is under 100\r\n";
    //                     }
    //                 }
    //
    //
    //                 var selLen = 0;
    //                 var skeys = Object.keys(selectBoxes);
    //                 var keyLen = skeys.length
    //                 var counter=0;
    //                 while(selLen == 0 && counter < keyLen) {
    //                     selLen = Object.keys(selectBoxes[skeys[counter++]]).length
    //                 }
    //
    //                 if(selLen == 0) {
    //                     valid = false;
    //                     errorMsg = "You have not saved anything!"
    //                 }
    //
    //                 if(!valid) {
    //                    alert(errorMsg);
    //                     return false;
    //                 } else if (warn) {
    //                     alert("WARNING:\r\n" + warnMsg);
    //                 }
    //
    //                 data['successBoxes'] = JSON.stringify(selectBoxes);
    //                 data['responses'] = JSON.stringify(responses);
    //                 data['year'] = workingYear;
    //
    //                 $.post(window.fmBaseDir + 'savePortfolioProjectJobs', data)
    //                     .then(function(res) {
    //                         var hasNoErrors = $.isEmptyObject(res.errors);
    //                         if (hasNoErrors) {
    //                             displaySuccessMsg();
    //                             $(that).dialog('close');
    //                         } else {
    //                             throwValidationErrors(res.errors);
    //                         }
    //                     })
    //                     .fail(displayErrorMsg);
    //             }
    //         }
    //     ]
    // });
}

function setSelectedBtn(el) {
    removeSelectedBtnClassFromAll();
    $(el).addClass('fm-btn-selected');
}

function removeSelectedBtnClassFromAll() {
    $('.fm-right-info-buttonset input[type="button"]').removeClass('fm-btn-selected');
}

function setFormSubmitHandler() {
    $('form.fm-right-form').submit(function(e) {
        e.preventDefault();
        $('#fm-submit-btn').button('disable');

        var data = getFormData(this);
        var isCreateChild = $('#create-child-btn').hasClass('fm-btn-selected');
        if (isCreateChild) {
            createEmployee(data);
        } else {
            updatePortfolio(data);
        }
    });
}

function getFormData(form) {
    return $(form).serializeArray().reduce(function(obj, item) {
        obj[item.name] = item.value;
        return obj;
    }, {});
}

function createEmployee(data) {
    var currentlySelectedNode = $('#jstree').jstree(true).get_selected(true)[0];
    data.parentId = currentlySelectedNode.id !== window.rootNodeId ? currentlySelectedNode.id : null;

    $.post(window.fmBaseDir + 'createEmployee', data)
        .then(function(res) {
            var hasNoErrors = $.isEmptyObject(res.errors);
            if (hasNoErrors) {
                createNewEmployeeoInJSTree(res.saveData);
                displaySuccessMsg();
                toggleExportButtonVisibility();
            } else {
                throwValidationErrors(res.errors);
            }
        })
        .fail(displayErrorMsg)
        .done(enableFormSubmitBtn);
}

function createNewEmployeeoInJSTree(saveData) {
    var jstree = $('#jstree').jstree(true);
    var parentId = saveData.data.parentId != null ? saveData.data.parentId : window.rootNodeId;
    var parentIsLoaded = jstree.is_loaded(parentId);

    jstree.deselect_all();

    if (true) {
        var node = {
            id: saveData.id,
            text: saveData.text,
            state: {
                selected: true,
            },
            data: saveData.data,
            children: saveData.hasChildren
        };

        jstree.create_node(parentId, node, '', function() {
            jstree.open_node(parentId, setJSTreeSelectionLogic(saveData.data));
        });
    } else {
        jstree.load_node(parentId, function() {
            jstree.select_node(saveData.id);
        });
    }
}



function throwValidationErrors(errorsObj) {
    Object.keys(errorsObj).forEach(function(key) {
        $('#fm-' + key + '-input').addClass('fm-error');
        $('#fm-' + key + '-validation-msg').text(errorsObj[key]);
    });
    displayValidationMsg();
}

function setFormInputHandler() {
    $(document).on('input', '.fm-form-input', function() {
        $('.fm-message .fm-error-msg').remove();
        $(this).removeClass('fm-error');
        $(this).siblings('.fm-validation-msg').text('');
    })
}

function displaySuccessMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-success-msg').text(window.fmSuccessMsg);
    $('.fm-message').append(msg);
}

function displayValidationMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-error-msg').text(window.fmErrorMsg);
    $('.fm-message').append(msg);
}

function displayErrorMsg() {
    removeHeaderMsg();
    var msg = $('<div>').addClass('fm-error-msg').text(window.fmProblemMsg);
    $('.fm-message').append(msg);
}

function removeHeaderMsg() {
    $('.fm-message .fm-success-msg, .fm-message .fm-error-msg').remove();
}






function getCurrentWorkItems(){

    var jstree = $('#jstree').jstree(true);
    var currentNodeId = jstree.get_selected();
    var parentNodeId = jstree.get_parent(currentNodeId);
    var data = {
        id: currentNodeId[0]
    };

    $.post(window.fmBaseDir + 'currentWork', data)
        .then(function(res) {

        })
        .fail(displayErrorMsg);

}

function enableFormSubmitBtn() {
    $('#fm-submit-btn').button('enable');
}

function handleDragAndDrop(e, data) {
    var jstree = $('#jstree').jstree(true);
    var parentNodeId = data.parent;

    jstree.open_node(parentNodeId);

    var postData = {
        id: data.node.id,
        parentId: parentNodeId === 'root' ? null : parentNodeId
    };
    $.post(window.fmBaseDir + 'updateParentOfEmployee', postData)
        .fail(function() {
            displayErrorMsg();
            jstree.refresh();
        });
}

function isDroppable(parentNode, more) {
    var isOutOfTree = parentNode.id === '#';
    var isTryingToReorder = more.pos !== 'i';

    if (isOutOfTree || isTryingToReorder) {
        return false;
    }

    return true;
}

function toggleExportButtonVisibility() {
    var jstree = $('#jstree').jstree(true);
    var rootNode = jstree.get_node(window.rootNodeId);

    if (rootNode.children && rootNode.children.length > 0) {
        $('#exportPortfolios').show();
    } else {
        $('#exportPortfolios').hide();
    }
}

function deleteRow(index,empId) {
    var elm = $("tr[id='"+index+"_"+empId+"']");

    $("#empRowId").html($("#editpersongoalstable tbody tr").index(elm) + 1);
    $( function() {
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "Delete Row": function() {

                    var that = this;
                    var data= {};
                    var selectBox = {};
                    $.each($(elm).find('select'), function (index, val) {
                        selectBox[$(val).attr('name')] = $(val).val();
                    });

                    data['selectBox'] = JSON.stringify(selectBox);
                    data['empId'] = empId;
                    data['index'] = index;
                    $.post(window.fmBaseDir + 'deleteWorkItem', data)
                        .then(function(res) {
                            if(res.status == "success") {
                                $(elm).empty();
                                $(elm).remove();
                            }
                            $(that).dialog("close");
                        }).done(function(){
                            $(that).dialog("close");
                    });
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    } );
}