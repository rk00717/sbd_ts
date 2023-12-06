<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="com.rkode.Utils" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script type="module" src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
        // This will fetch the client data based on the provided uuid
        function fetchData(uuid) {
            $.ajax({
                type: 'GET', 
                url: '/fetch_clients_data', 
                success: function (response) {
                    for (var i = 0; i < response.length; i++) {
                        var user = response[i];

                        if(user.uuid === uuid){
                            $('#user_first_name').val(user.first_name);
                            $('#user_last_name').val(user.last_name);
                            $('#user_street').val(user.street);
                            $('#user_city').val(user.city);
                            $('#user_email').val(user.email);
                            $('#user_address').val(user.address);
                            $('#user_state').val(user.state);
                            $('#user_phone').val(user.phone);
                        }
                    }
                },
                error: function (error) {
                    console.error("Failed to fetch data...");
                }
            });
        }
    </script>
</head>
<body class="bg-dark text-light">
    <div class="container-sm d-flex flex-column p-5 m-sm justify-content-center">
        <h1 class="text-center">Client Details</h1>
        <!-- Trying to fetch uuid if there is one in the parameters the 
             we will setup the form to update the details else 
             we will create a new client -->
        <% String uuid = request.getParameter("uuid"); %>
        <% if(uuid == null){ %>
        <form action="/create_client" method="post">
        <% } else { %>
        <script> fetchData('<%= uuid %>'); </script>
        <form action="/update_client?uuid=<%= uuid %>" method="post">
        <% } %>
            <div class="d-flex gap-5 p-2">
                <div class="row gap-2">
                    <input id="user_first_name" name="user_first_name" class="form-control" placeholder="First name">
                    <input id="user_street" name="user_street" class="form-control" placeholder="Street">
                    <input id="user_city" name="user_city" class="form-control" placeholder="City">
                    <input id="user_email" name="user_email" type="email" class="form-control" placeholder="E-mail">
                </div>
                <div class="row gap-2">
                    <input id="user_last_name" name="user_last_name" class="form-control" placeholder="Last name">
                    <input id="user_address" name="user_address" class="form-control" placeholder="Address">
                    <input id="user_state" name="user_state" class="form-control" placeholder="State">
                    <input id="user_phone" name="user_phone" type="tel" class="form-control" placeholder="Phone">
                </div>
            </div>
            <input style="float: right;" class="btn btn-primary" type="submit" value="Submit">
        </form>
    </div>
</body>
</html>