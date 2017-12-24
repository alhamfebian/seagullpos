<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/19/2017
  Time: 11:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SeagullPOS - Order</title>

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
                <div class=" container-fluid">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="wrapper-order">
                                <div class="form-group col-sm-6 pull-left">
                                    <input type="text" placeholder="search" id="search-item" name="search-item" class="form-control">
                                </div>

                                <div id="category-filter" class="pull-right col-sm-2">
                                    <span>All Category</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="wrapper-order">
                                <div class="col-sm-4 pull-left">
                                    <span class="glyphicon glyphicon-plus"></span>
                                </div>
                                <div class="form-group col-sm-6 pull-right">
                                    <input type="text" placeholder="search" id="search-member" name="search-mmeber" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="wrapper-item">

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="list-item">
                                <div>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td>Id</td>
                                                <td>Name</td>
                                                <td>Price</td>
                                                <td>Qty</td>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        $('#order-page').siblings('a').removeClass('active');
        $('#order-page').addClass('active');
    </script>

</body>
</html>
