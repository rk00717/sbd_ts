<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Database</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script type="module" src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </head>
<body class="bg-dark text-light">
    <div class="container-sm d-flex flex-column p-sm m-sm justify-content-center">
        <div id="data_container" class="p-5">
            <div class="d-flex">
                <!-- This will redirect to the page where user can able to fill the form for new client entry -->
                <form action="/user_details.jsp" method="post">
                    <input class="btn btn-primary position-absolute" type="submit" value="Add Customer">
                </form>
                <h1 class="text-center flex-grow-1">Clients Data</h1>
            </div>
            <table class="table">
                <thead>
                    <tr class="table-dark">
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Street</th>
                        <th scope="col">Address</th>
                        <th scope="col">City</th>
                        <th scope="col">State</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody id="client_data_body">
                </tbody>
            </table>
        </div>
        <% 
            // #############################################################
            // Trying to fetch responseCode from the session 
            // Will try to show the message according to the status message
            // #############################################################
            try{
                String responseCodeStr = session.getAttribute("status").toString();
                int responseCode = Integer.parseInt(responseCodeStr);
                
                session.removeAttribute("status");
                
                System.out.println("Response Status : " + responseCode);
                if(responseCode != 0){
        %>
                    <% if(responseCode >= 200 && responseCode <= 205){ %>
                    <div id="error_message" class="card container-sm border border-success bg-black">
                        <div class="text-center text-success">
                    <% } else { %>
                    <div id="error_message" class="card container-sm border border-danger bg-black">
                        <div class="text-center text-danger">
                    <% } %>
                            <%= responseCode %>
                        </div>
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
            } catch(Exception e){
                System.out.println("Status not found " + e);
            }
        %>
    </div>
    <script>
        // This function will try to get the clients list from the server and show here on the webpage
        $(document).ready(function () {
            fetchData();
            
            function fetchData() {
                $.ajax({
                    type: 'GET', 
                    url: '/fetch_clients_data', 
                    success: function (response) {
                        updateTable(response);
                    },
                    error: function (error) {
                        console.error("Failed to fetch data...");
                    }
                });
            }
        
            function updateTable(data) {
                $('#client_data_body').empty(); 
    
                for (var i = 0; i < data.length; i++) {
                    var user = data[i];
                    $('#client_data_body').append(
                        '<tr id=\"' + user.uuid + '_data\" class="table-dark"><td data-field=\"first_name\">' + user.first_name + 
                        '</td><td data-field=\"last_name\">' + user.last_name + 
                        '</td><td data-field=\"street\">' + user.street + 
                        '</td><td data-field=\"address\">' + user.address + 
                        '</td><td data-field=\"city\">' + user.city + 
                        '</td><td data-field=\"state\">' + user.state + 
                        '</td><td data-field=\"email\">' + user.email + 
                        '</td><td data-field=\"phone\">' + user.phone + 
                        '</td><td>' + 
                        '<form action=\"/user_details.jsp?uuid='+ user.uuid +'\" method=\"post\" class=\"d-inline p-2\"><input class=\"btn btn-primary rounded-pill\" type=\"submit\" value=\"Edit\"></form>' + 
                        '<input class=\"btn btn-danger rounded-pill\" type=\"submit\" value=\"&#128465;\" onclick="requestToRemoveUser(\''+ user.uuid +'\')">' + 
                        '</td></tr>'
                    );
                }
            }
        });
        
        // This will request to remove the client from the server
        function requestToRemoveUser(uuid){
            $.ajax({
                type: 'POST',
                url: '/delete_client?uuid='+uuid,
                success: function (response) {
                    location.reload();
                    console.log("Successfully Deleted entry...");

                },
                error: function (response) {
                    console.log("Error while deleting entry...");
                }
            });
        }
    </script>
</body>
</html>