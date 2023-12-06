package com.rkode.operations;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rkode.Constant;
import com.rkode.Utils;

@WebServlet("/create_client")
public class CreateClient extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String access_token = Utils.GetAccessToken(request);

        // If we have access token only then we will allow the procedure
        // to create a new client in the data base
        if (access_token != null) {
            // trying to setup a connection and create a new client
            try {
                // Setting up the connections
                URL url = new URL(Constant.CREATE_CLIENT_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + access_token);
                connection.setDoOutput(true);

                // Fetching required parameter from request
                String first_name = request.getParameter("user_first_name");
                String street = request.getParameter("user_street");
                String city = request.getParameter("user_city");
                String email = request.getParameter("user_email");
                String last_name = request.getParameter("user_last_name");
                String address = request.getParameter("user_address");
                String state = request.getParameter("user_state");
                String phone = request.getParameter("user_phone");

                // Writing the data for the packet
                String jsonBody = Utils.GetClientBody(
                        first_name, last_name, street, address, city, state, email, phone);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                request.getSession().setAttribute("status", responseCode);

                connection.disconnect();
            } catch (Exception e) {
                // will set the response status as the error
                request.getSession().setAttribute("status", "Something went wrong!!!");
            }
        } else {
            // will set the response status as the error
            request.getSession().setAttribute("status", "Authorization Error");
        }

        // On complete we will redirect the user back to the page
        // where he can see all the clients
        response.sendRedirect("/users_list.jsp");
    }
}