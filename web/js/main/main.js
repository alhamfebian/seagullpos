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

retrieveData();

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
};

// bad implementation
$("#btn-register").click(function (e) {
    e.preventDefault();
    console.log($("#register-staff-form").serialize());

    if(true){
        $.ajax({
            url : "/user",
            type : "POST",
            data : $("#register-staff-form").serialize(),
            success : retrieveData,
            error : function () {
                alert("waduh");
            }
        })
    }
});


function processUserData(data) {
    var listUser = data == null ? [] : data;

    $("#staff-data tbody tr").remove();

    var tableData = $("#staff-data tbody");

    console.log(listUser);
    $.each(listUser, function (key, value) {
        tableData.append(
            "<tr>" +
            "<td>" + value.employeeId + "</td>" +
            "<td>" + value.employeeName + "</td>" +
            "<td>" + value.employeeEmail + "</td>" +
            "<td>" + value.employeeGender + "</td>" +
            "<td>" + value.lastLogin + "</td>" +
            "<td>" + value.employeeRole + "</td>" +
            "<td>" +
            "<span class='glyphicon glyphicon-pencil icon-margin cursor-point text-success' id='icon-update-user'></span>" +
            "<span class='glyphicon glyphicon-trash cursor-point text-warning' id='icon-delete-user'></span>" +
            "</td>" +
            "</tr>"
        )
    });
}


$("#staff-data").on("click", "#icon-delete-user" ,function () {

    var tr = $(this).parent().parent();

    var staffId = tr.find("td:first").html();

    $.ajax({
        url : "/user/delete?" + staffId,
        type : "DELETE",
        success : retrieveData,
        error : function () {
            alert("error");
        }
    })
});