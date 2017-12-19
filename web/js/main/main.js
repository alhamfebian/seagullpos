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

var url = "";

function setPath(apiURL) {
    url = apiURL;
}

function retrieveDataPaging(pagingURL){
    $.ajax({
        url : pagingURL,
        dataType : "JSON",
        type : "GET",
        success : function (data) {
            if(url == "/user"){
                processUserData(data.content);
                $('#total-user').html('Total User : ' + data.totalRecords + " user(s)");
            }
            else if(url == "/member"){
                processMemberData(data.content);
                $('#total-member').html('Total Member : ' + data.totalRecords + " member(s)");
            }

            paginateLink(data.totalRecords, data.pageSize, data.currentPage);
        },
        error : function () {
            alert("error");
        }
    })
}

function insertData(data){
    $.ajax({
        url : url,
        type : "POST",
        data : data,
        success : function () {
            retrieveDataPaging(url);
        },
        error : function () {
            alert("waduh");
        }
    })
}

function searchData(pageURL) {
    console.log(pageURL);
    $.ajax({
        url : pageURL,
        dataType : "JSON",
        type : "GET",
        success : function (data) {
            if(url == "/user"){
                processUserData(data.content);
                $('#total-user').html('Total User : ' + data.totalRecords + " user(s)");
            }else if(url == "/member"){
                processMemberData(data.content);
                $('#total-member').html('Total Member : ' + data.totalRecords + " member(s)");
            }
            paginateLink(data.totalRecords, data.pageSize, data.currentPage, pageURL);
        },
        error : function () {
            alert("error");
        }
    })
}

function updateData(data){
    console.log(data);

    $.ajax({
        url : url + "/update?id=" + data.employeeId,
        type : "PUT",
        contentType : "application/json",
        data : JSON.stringify(data),
        success : function () {
            retrieveDataPaging(url);
        },
        error : function (ts) {
            alert(ts.responseText);
        }
    })
}

function changePassword(employeeId, newPassword){
    $.ajax({
        url : url + "/update/changepassword?id=" + employeeId,
        type : "PUT",
        contentType : "application/json",
        data : JSON.stringify({ employeeId : employeeId, employeePassword : newPassword }),
        success : function () {
            alert("success");
            $('#changePassword').modal('hide');
            retrieveDataPaging(url);
        },
        error : function () {
            alert("haha");
        }
    })
}

function deleteData(id){
    $.ajax({
        url : url + "/delete?id=" + id,
        type : "DELETE",
        success : function () {
            retrieveDataPaging(url);
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
    obj.lastLogin = obj.lastLogin == "Haven't login yet" ? null : obj.lastLogin;
    updateData(obj);
}

function processUserData(data) {
    var listUser = data == null ? [] : data;

    $("#staff-data tbody tr").remove();

    var tableData = $("#staff-data tbody");

    $.each(listUser, function (key, value) {
        value.lastLogin = (value.lastLogin === null) ? 'Haven\'t login yet': value.lastLogin;
        tableData.append(
            "<tr>" +
            '<td class="col-sm-1 not-editable" data-employee-id="' + value.employeeId + '">' + value.employeeId + "</td>" +
            '<td class="col-sm-2 editable" data-employee-name="' + value.employeeName + '">' + value.employeeName + "</td>" +
            '<td class="col-sm-2 editable" data-employee-email="' + value.employeeEmail + '">' + value.employeeEmail + "</td>" +
            '<td class="col-sm-1 selection" data-employee-gender="' + value.employeeGender + '">' + value.employeeGender + "</td>" +
            '<td class="col-sm-3 not-editable" data-last-login="' + value.lastLogin + '">' + value.lastLogin + "</td>" +
            '<td class="col-sm-1 selection" data-employee-role="' + value.employeeRole + '">' + value.employeeRole + "</td>" +
            "<td class='col-sm-2 action-icon'>" +
            "<span class='glyphicon glyphicon-pencil icon-margin cursor-point text-success icon-update'></span>" +
            "<span class='glyphicon glyphicon-trash cursor-point text-warning icon-margin icon-delete'></span>" +
            "<span class='glyphicon glyphicon-lock cursor-point text-danger' id='icon-password-user' data-toggle='modal' " +
            "data-target='#changePassword'></span>" +
            "</td>" +
            "</tr>"
        )
    });
}

function processMemberData(data) {
    var listMember = data == null ? [] : data;

    $("#member-data tbody tr").remove();

    var tableData = $("#member-data tbody");

    $.each(listMember, function (key, value) {
        tableData.append(
            "<tr>" +
            "<td class='col-sm-1 not-editable' data-member-id='" + value.memberId +"'>" + value.memberId +"</td>" +
            "<td class='col-sm-2 editable' data-member-name='" + value.memberName + "'>" + value.memberName + "</td>" +
            "<td class='col-sm-2 editable' data-member-email='" + value.memberEmail + "'>" + value.memberEmail + "</td>" +
            "<td class='col-sm-1 editable' data-phone-number='" + value.phoneNumber + "'>" + value.phoneNumber + "</td>" +
            "<td class='col-sm-1 not-editable' data-total-transaction='" + value.totalTransaction + "'>" + value.totalTransaction + "</td>" +
            "<td class='col-sm-1 not-editable' data-member-point='" + value.memberPoint + "'>" + value.memberPoint + "</td>" +
            "<td class='col-sm-2 not-editable' data-register-date='" + value.registerDate + "'>" + value.registerDate + "</td>" +
            "<td class='col-sm-1 action-icon'>" +
            "<span class='glyphicon glyphicon-pencil icon-margin cursor-point text-success icon-update'></span>" +
            "<span class='glyphicon glyphicon-trash cursor-point text-warning icon-margin icon-delete'></span>" +
            "</td>" +
            "</tr>"
        )
    })
}

function paginateLink(total ,limit, currentPage, currentURL) {
    var numberOfLinks = Math.ceil(total / limit);
    var paginate = $('.pagination');
    var prev = (currentPage == 1) ? 1 : (currentPage - 1);
    var next = (currentPage == numberOfLinks) ? numberOfLinks : (currentPage + 1);
    var pageURL = setPagingLink(currentURL);

    console.log(total + " " + pageURL);

    paginate.empty();
    for(var i = 1; i <= numberOfLinks; i++){
        paginate.append('<li><a href="' + pageURL + i + ' ">' +
            i + '</a></li>');
    }
    paginate.prepend('<li><a href="' + pageURL + prev + '"><span aria-hidden="true">&laquo;</span></a></li>');
    paginate.append('<li><a href="' + pageURL + next +' "><span>&raquo;</span></a></li>');
    paginate.find('li').eq(currentPage).addClass('active');
}

function setPagingLink(currentURL){
    var pageURL = url;

    if(currentURL != null){
        var index = currentURL.search("&page");
        if(index == -1)
            pageURL = currentURL + "&page=";
        else{
            pageURL = currentURL.substr(0, index) + "&page=";
        }
    }
    else{
        pageURL = pageURL + "?page=";
    }

    return pageURL;
}

$('.pagination').on("click", "a", function (e) {
    e.preventDefault();
    var url = $(this).attr('href');
    $(this).parent().siblings('li').removeClass('active');
    console.log(url);
    if(url.indexOf('&') == -1)
        retrieveDataPaging(url);
    else
        searchData(url);
});

$(".search-item").keyup(function () {
    var searchValue = $(this).val();

    if(searchValue == ''){
        retrieveDataPaging(url);
    }else{
        var searchURL = url + "/search?query=" + searchValue;
        searchData(searchURL);
    }

});

$("#btn-register").click(function (e) {
    e.preventDefault();
    var form = $('.modal-body').find('form');
    var data = form.serialize();
    if(true){
        insertData(data);
    }
});

$("#btn-change-password").click(function (e) {
    e.preventDefault();

    var employeeId = $('#employeeId').val();
    var employeePassword = $('#employeePassword').val();

    if(true){
        changePassword(employeeId, employeePassword);
    }
});

$(".list-table-data").on("click", ".icon-update", function () {
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

    $(this).removeClass("glyphicon-pencil icon-update").addClass("glyphicon-ok").attr("id", "update-data");
    $(this).siblings().removeClass("glyphicon-trash icon-delete").addClass("glyphicon-remove").attr("id", "cancel-update");
});

$(".list-table-data").on("click", "#update-data", function () {
   formToJSON($(this).parent().parent());
});

$(".list-table-data").on("click", "#cancel-update", function () {
    var tr = $(this).parent().parent();

    tr.find(":input").each(function () {
        if($(this).parent().hasClass("selection")){
            var data = $(this).data("selected");
            $(this).parent().html(data);
        }else {
            $(this).parent().html($(this).prop("defaultValue"));
        }
    });


    $(this).siblings().removeClass("glyphicon-ok").addClass("glyphicon-pencil icon-update");
    $(this).removeClass("glyphicon-remove").addClass("glyphicon-trash icon-delete");
});

$(".list-table-data").on("click", ".icon-delete" ,function () {

    var tr = $(this).parent().parent();

    var id = tr.find("td:first").text();
    console.log(id);
    deleteData(id);
});

$("#staff-data").on("click", "#icon-password-user", function () {
    var tr = $(this).parent().parent();

    var staffId = tr.find("td:first").text();
    var staffName = tr.find("td").eq(1).text();

    $('#employeeId').val(staffId);
    $('#employeeName').val(staffName);
});
