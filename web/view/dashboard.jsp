<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 11/1/2017
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seagull POS - Dashboard</title>

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
                    <h1>Ledger</h1>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
