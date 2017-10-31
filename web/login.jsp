<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/31/2017
  Time: 9:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seagull POS - Login</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/custom/main.css">

    <script src="js/jquery-3.2.1.js"></script>

</head>
<body>
    <div class="container-flex">
        <div class="login-container">
            <form action="" class="login-form">
                <h3>Login</h3>
                <hr>
                <div class="form-group">
                    <label for="user-email">Email</label>
                    <input type="text" class="form-control" id="user-email" placeholder="Email">
                </div>

                <div class="form-group">
                    <label for="user-password">Password</label>
                    <input type="password" class="form-control" id="user-password" placeholder="Password">
                </div>

                <button class="btn btn-block btn-primary">Login</button>
            </form>
        </div>
    </div>
</body>
</html>
