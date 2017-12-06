function validateEmailFormat(email){
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    return pattern.test(email);
}

function validateLoginForm() {

    $('.help-block').remove();
    $('#glyph-password').remove();
    $('#glyph-email').remove();

    var email = $("#user-email").val();
    var password = $("#user-password").val();
    var check = true;

    if(email === ""){
        check = false;
        var formDiv = $('#user-email').closest('.form-group');
        formDiv.addClass('has-error has-feedback');
        formDiv.append("<span id='glyph-email' class='glyphicon glyphicon-remove form-control-feedback'></span>");
        formDiv.append("<span class='help-block'>This field is required.</span>");
    }
    else if(!validateEmailFormat(email)){
        check = false;
        var formDiv = $('#user-email').closest('.form-group');
        formDiv.addClass('has-error has-feedback');
        formDiv.append("<span id='glyph-email' class='glyphicon glyphicon-remove form-control-feedback'></span>");
        formDiv.append("<span class='help-block'>Please enter valid email address.</span>");
    }
    else{
        var formDiv = $('#user-email').closest('.form-group');
        formDiv.removeClass('has-error');
        formDiv.addClass('has-success');
        formDiv.append("<span id='glyph-email' class='glyphicon glyphicon-ok form-control-feedback'></span>");
    }

    if(password === ""){
        check = false;
        var formDiv = $('#user-password').closest('.form-group');
        formDiv.addClass('has-error has-feedback');
        formDiv.append("<span id='glyph-password' class='glyphicon glyphicon-remove form-control-feedback'></span>");
        formDiv.append("<span class='help-block'>This field is required.</span>");
    }
    else{
        var formDiv = $('#user-password').closest('.form-group');
        formDiv.removeClass('has-error');
        formDiv.addClass('has-success');
        formDiv.append("<span id='glyph-password' class='glyphicon glyphicon-ok form-control-feedback'></span>");
    }

    return check;
}

$("#btn-login").click(function (e) {
    e.preventDefault();
    if(validateLoginForm()){
        $('#login-form').submit();
    }
});


retrieveDataPaging(1);

function retrieveData(){
    $.ajax({
        url : "/user",
        dataType : "JSON",
        type : "GET",
        success : processUserData,
        error : function () {
            alert("error");
        }
    })
}

function retrieveDataPaging(pageNumber){
    $.ajax({
        url : "/user/" + "userdata?page=" + pageNumber,
        dataType : "JSON",
        type : "GET",
        success : function (data) {
            processUserData(data);
            setTimeout(getTotalData(pageNumber, ""), 1000);
        },
        error : function () {
            alert("error");
        }
    })
}

function getTotalData(pageNumber, search){
    $.ajax({
        url : "/user/totaldata?query=" + search,
        dataType : "JSON",
        type : "GET",
        success : function (data) {
            $('#total-user').html('Total User : ' + data + " user(s)");
            createPaginate(data, pageNumber, search);
        },
        error : function () {
            alert("error");
        }
    })
}

function insertData(){
    $.ajax({
        url : "/user",
        type : "POST",
        data : $("#register-staff-form").serialize(),
        success : function () {
            retrieveDataPaging(1);
        },
        error : function () {
            alert("waduh");
        }
    })
}

function searchData(searchValue, pageNumber) {
    console.log(searchValue);
    $.ajax({
        url : "/user/search?query=" + searchValue + "&page=" + pageNumber,
        dataType : "JSON",
        type : "GET",
        success : function (data) {
            processUserData(data);
            getTotalData(pageNumber, searchValue);
        },
        error : function () {
            alert("error");
        }
    })
}

function updateData(data){
    console.log(data);

    $.ajax({
        url : "/user/update?id=" + data.employeeid,
        type : "PUT",
        contentType : "application/json",
        data : JSON.stringify(data),
        success : function () {
            retrieveDataPaging(1);
        },
        error : function (ts) {
            alert(ts.responseText);
        }
    })
}

function deleteData(staffId){
    $.ajax({
        url : "/user/delete?id=" + staffId,
        type : "DELETE",
        success : function () {
            retrieveDataPaging(1);
        },
        error : function () {
            alert("error");
        }
    })
}

function formToJSON(tr){
    var obj = {};
    tr.find(":input").each(function () {
        if($(this).val() == 'undefined') obj[$(this).attr('id')] = null;
        else obj[$(this).attr('id')] = $(this).val();
    });

    updateData(obj);
}

function createPaginate(data, pageNumber, search) {
    var total = data;
    var paginate = $('.pagination');
    var numberOfLinks = Math.ceil(total / 10);
    var prev = (pageNumber == 1) ? 1 : (pageNumber - 1);
    var next = (pageNumber == numberOfLinks) ? numberOfLinks : (pageNumber + 1);

    paginate.empty();

    paginate.data("search", search);

    paginate.append('<li><a href="#" data-page="' + prev + '"><span aria-hidden="true">&laquo;</span></a></li>');

    for(var i = 0; i < numberOfLinks; i++){
        paginate.append('<li><a href="#" data-page="' + (i + 1) + '">' +
            (i + 1) + '</a></li>');
    }

    paginate.append('<li><a href="#" data-page="' + next + '"><span>&raquo;</span></a></li>');
    paginate.find('li').eq(pageNumber).addClass('active');
}

function processUserData(data) {
    var listUser = data == null ? [] : data;

    $("#staff-data tbody tr").remove();

    var tableData = $("#staff-data tbody");

    console.log(listUser);
    $.each(listUser, function (key, value) {
        tableData.append(
            "<tr>" +
            '<td class="col-sm-1 not-editable" data-employee-id="' + value.employeeId + '">' + value.employeeId + "</td>" +
            '<td class="col-sm-2 editable" data-employee-name="' + value.employeeName + '">' + value.employeeName + "</td>" +
            '<td class="col-sm-3 editable" data-employee-email="' + value.employeeEmail + '">' + value.employeeEmail + "</td>" +
            '<td class="col-sm-1 selection" data-employee-gender="' + value.employeeGender + '">' + value.employeeGender + "</td>" +
            '<td class="col-sm-3 not-editable" data-last-login="' + value.lastLogin + '">' + value.lastLogin + "</td>" +
            '<td class="col-sm-1 selection" data-employee-role="' + value.employeeRole + '">' + value.employeeRole + "</td>" +
            "<td class='col-sm-1 action-icon'>" +
            "<span class='glyphicon glyphicon-pencil icon-margin cursor-point text-success' id='icon-update-user'></span>" +
            "<span class='glyphicon glyphicon-trash cursor-point text-warning' id='icon-delete-user'></span>" +
            "</td>" +
            "</tr>"
        )
    });

}

$('.pagination').on("click", "a", function () {
    var pageNumber = $(this).data('page');
    var search = $(this).parent().parent().data("search");
    $(this).parent().siblings('li').removeClass('active');
    //$(this).parent().addClass('active');
    console.log(search);

    if(search == '')
        retrieveDataPaging(pageNumber);
    else
        searchData(search, pageNumber);
});

$("#btn-register").click(function (e) {
    e.preventDefault();
    console.log($("#register-staff-form").serialize());

    if(true){
        insertData();
    }
});

$("#staff-data").on("click", "#icon-update-user", function () {

    var tr = $(this).parent().parent();

    var attribute = { id : "", value : "", class : "form-control", disabled : false };

    tr.find("td").each(function () {
        var input = $('<input type="text">');

        $.each($(this).data(), function (key, value) {
            attribute.id = key;
            attribute.value = value;
        });

       if($(this).hasClass('editable')){
            attribute.disabled = false;
            input.attr(attribute);
            $(this).html(input);
       }
       else if($(this).hasClass('not-editable')){
            attribute.disabled = true;
            input.attr(attribute);
            $(this).html(input);
       }
       else if($(this).hasClass('selection')){

            if(attribute.id === "employeeRole"){
                $(this).html(
                    '<select id="' + attribute.id + '" class="form-control" data-selected="' + attribute.value + '">' +
                    '<option>Admin</option>' +
                    '<option>Cashier</option>' +
                    '</select>'
                );
            }else if(attribute.id === "employeeGender"){
                $(this).html(
                    '<select id="' + attribute.id + '" class="form-control" data-selected="' + attribute.value + '">' +
                    '<option>Male</option>' +
                    '<option>Female</option>' +
                    '</select>'
                );
            }

       }
    });

    $(this).removeClass("glyphicon-pencil").addClass("glyphicon-ok").attr("id", "update-data");
    $(this).siblings().removeClass("glyphicon-trash").addClass("glyphicon-remove").attr("id", "cancel-update");
});

$("#staff-data").on("click", "#update-data", function () {
   formToJSON($(this).parent().parent());
});

$("#staff-data").on("click", "#cancel-update", function () {
    var tr = $(this).parent().parent();

    tr.find(":input").each(function () {
        if($(this).parent().hasClass("selection")){
            var data = $(this).data("selected");
            $(this).parent().html(data);
        }else {
            $(this).parent().html($(this).prop("defaultValue"));
        }
    });


    $(this).siblings().removeClass("glyphicon-ok").addClass("glyphicon-pencil").attr("id", "icon-update-user");
    $(this).removeClass("glyphicon-remove").addClass("glyphicon-trash").attr("id", "icon-delete-user");
});

$("#staff-data").on("click", "#icon-delete-user" ,function () {

    var tr = $(this).parent().parent();

    var staffId = tr.find("td:first").text();

    deleteData(staffId);
});

$("#search-item").keyup(function () {
   var searchValue = $(this).val();

   if(searchValue == ''){
       retrieveDataPaging(1);
   }else{
       searchData(searchValue, 1);
   }

});