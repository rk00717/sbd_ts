<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script type="module" src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-dark text-light">
    <% session.removeAttribute("responseData"); %>
    <div class="container-sm d-flex flex-column p-5 m-sm justify-content-center">
        <h1 class="text-center">Log-in</h1>
        <form action="auth_user" method="post" class="d-flex flex-column justify-content-center">
            <div class="mb-3">
                <label for="id_input" class="form-label">Login ID : </label>
                <input id="login_id" name="id_input" type="email" class="form-control" value="test@sunbasedata.com">
            </div>
            <div class="mb-3">
                <label for="pwd_input" class="form-label">Password : </label>
                <input id="login_pwd" name="pwd_input" type="password" class="form-control" value="Test@123">
            </div>
            <input class="btn btn-primary" type="submit" value="Login">
        </form>
    <% 
        String error = request.getParameter("error");
        if (error != null && error.equals("invalid-authentication-code")) {
    %>
        <div id="error_message" class="card container-sm border border-danger bg-black">
            <div class="text-center text-danger">Invalid authentication code. Please try again.</div>
        </div>
        <script>
            // Hide the error message after 5 seconds
            setTimeout(function() {
                var errorMessage = document.getElementById("error_message");
                if (errorMessage) {
                    errorMessage.style.display = 'none';
                }
            }, 3000);
        </script>
    <%
        }
    %>
    </div>
</body>
</html>
