package com.rkode.operations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rkode.Constant;
import com.rkode.Utils;

@WebServlet("/delete_client")
public class DeleteClient extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String access_token = Utils.GetAccessToken(request);

        // If we have access token only then we will allow the procedure
        // to delete the respective client from the data base
        if (access_token != null) {
            // trying to setup a connection and remove client
            try {
                String uuid = request.getParameter("uuid");

                // Setting up the connections
                URL url = new URL(Constant.REMOVE_CLIENT_API + uuid);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + access_token);
                connection.setDoOutput(true);

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