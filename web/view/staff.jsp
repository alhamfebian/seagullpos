<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seagull POS - Staff</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../css/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/custom/main.css">

    <script src="../js/jquery-3.2.1.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
    <%@ include file="../layout/topnav.jsp" %>


    <div class="container-fluid" id="no-padding">
        <div class="row">
            <div class="col-md-3 col-lg-2">
                <%@ include file="../layout/sidenav.jsp"%>
            </div>
            <div class="col-md-9 col-lg-10">
                <div class="wrapper container-fluid">
                    <h1>Staff</h1>
                    <div class="row small-margin-bottom">

                        <div class="col-sm-2">
                            <button class="btn btn-primary" data-toggle="modal" data-target="#addStaffModal">
                                <span class="glyphicon glyphicon-plus icon-margin"></span>Add User
                            </button>
                        </div>

                        <div class="col-sm-4 pull-right">
                            <div class="input-group has-feedback">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        <span>Filter by</span> <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#">Name</a></li>
                                        <li><a href="#">Id</a></li>
                                        <li><a href="#">Role</a></li>
                                    </ul>
                                </div>
                                <input type="text" class="form-control search-item" name="search-item" placeholder="Search...">
                                <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-search"></span>
                                </span>

                            </div>
                        </div>
                    </div>

                    <span id="total-user">Total User : </span>

                    <table class="table table-striped table-responsive list-table-data" id="staff-data">
                        <thead>
                            <tr>
                                <td class="col-sm-1">Id</td>
                                <td class="col-sm-2">Name</td>
                                <td class="col-sm-2">Email</td>
                                <td class="col-sm-1">Gender</td>
                                <td class="col-sm-3">Last Login</td>
                                <td class="col-sm-1">Role</td>
                                <td class="col-sm-2">Action</td>
                            </tr>
                        </thead>

                        <tbody>

                        </tbody>
                    </table>
                    <nav aria-label="Page navigation">
                        <ul class="pagination">

                        </ul>
                    </nav>
                </div>
            </div>

        </div>
    </div>

    <div class="modal fade" id="addStaffModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">Staff Registration</h4>
                </div>
                <div class="modal-body">
                    <form id="register-staff-form">
                        <div class="form-group">
                            <label for="fullname">Full Name</label>
                            <input type="text" id="fullname" name="fullname" class="form-control" placeholder="fullname">
                        </div>

                        <div class="form-group">
                            <label for="staffEmail">Email</label>
                            <input type="text" id="staffEmail" name="staffEmail" class="form-control" placeholder="email">
                        </div>

                        <div class="form-group">
                            <label for="staffPassword">Password</label>
                            <input type="password" id="staffPassword" name="staffPassword" class="form-control" placeholder="password">
                        </div>

                        <div class="form-group">
                            <label for="confirmpassword">Confirm Password</label>
                            <input type="password" id="confirmpassword" name="confirmpassword" class="form-control" placeholder="confirm password">
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-6">
                                <label>Gender</label>
                                <div class="radio">
                                    <label class="radio-inline">
                                        <input type="radio" name="gender" id="inlineRadio1" value="Male"> Male
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="gender" id="inlineRadio2" value="Female"> Female
                                    </label>
                                </div>

                            </div>
                            <div class="col-sm-6">
                                <label>Role</label>
                                <select class="form-control" name="role" id="role">
                                    <option value="admin">Admin</option>
                                    <option value="cashier">Cashier</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="btn-register">Add Staff</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="changePassword">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="changePasswordLabel">Change Password</h4>
                </div>

                <div class="modal-body">
                    <form id="change-password">
                        <div class="form-group">
                            <label for="employeeId">Employee Id</label>
                            <input type="text" id="employeeId" name="employeeId" class="form-control" disabled>
                        </div>

                        <div class="form-group">
                            <label for="employeeName">Employee Name</label>
                            <input type="text" id="employeeName" name="employeeName" class="form-control" disabled>
                        </div>

                        <div class="form-group">
                            <label for="oldPassword">Old Password</label>
                            <input type="password" id="oldPassword" name="oldPassword" class="form-control">
                        </div>

                        <div class="form-group">
                            <label for="employeePassword">New Password</label>
                            <input type="password" id="employeePassword" name="employeePassword" class="form-control">
                        </div>

                        <div class="form-group">
                            <label for="reEmployeePassword">Re - enter New Password</label>
                            <input type="password" id="reEmployeePassword" name="reEmployeePassword" class="form-control">
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="btn-change-password">Change Password</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="../js/main/main.js"></script>
    <script>
        $('#staff-page').siblings('a').removeClass('active');
        $('#staff-page').addClass('active');
        setPath("/user");
        setTimeout(retrieveDataPaging("/user"), 1000);
    </script>
</body>
</html>
