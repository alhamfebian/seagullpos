<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/6/2017
  Time: 9:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seagull POS - Product</title>

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
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="#">Home</a></li>
                        <li role="presentation"><a href="#">Profile</a></li>
                        <li role="presentation"><a href="#">Messages</a></li>
                    </ul>
                    <h1>Product</h1>
                    <div class="row small-margin-bottom">

                        <div class="col-sm-2">
                            <button class="btn btn-primary" data-toggle="modal" data-target="#addProductModal">
                                <span class="glyphicon glyphicon-plus icon-margin"></span>Add Product
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



                    <span id="total-member">Total Product : </span>
                    <div class="table-responsive">
                        <table class="table table-bordered list-table-data" id="product-data">
                            <thead>
                            <tr>
                                <td class="col-sm-1">Id</td>
                                <td class="col-sm-2">Name</td>
                                <td class="col-sm-2">Email</td>
                                <td class="col-sm-1">Phone Number</td>
                                <td class="col-sm-1">Total Transaction</td>
                                <td class="col-sm-1">Total Point</td>
                                <td class="col-sm-2">Register Date</td>
                                <td class="col-sm-1">Action</td>
                            </tr>
                            </thead>

                            <tbody>

                            </tbody>
                        </table>
                    </div>

                    <nav aria-label="Page navigation">
                        <ul class="pagination">

                        </ul>
                    </nav>
                </div>
            </div>

        </div>
    </div>


    <script>
        $('#product-page').siblings('a').removeClass('active');
        $('#product-page').addClass('active');
    </script>

</body>
</html>
