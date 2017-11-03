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