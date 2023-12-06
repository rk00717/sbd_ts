package com.rkode.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rkode.Constant;
import com.rkode.Utils;

@WebServlet("/fetch_clients_data")
public class FetchClients extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String access_token = Utils.GetAccessToken(request);

        if (access_token != null) {
            try {
                // Setting up the connections
                URL url = new URL(Constant.CLIENT_INFO_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + access_token);
                connection.setDoOutput(true);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseStr = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        responseStr.append(line);
                    }

                    reader.close();

                    String responseData = responseStr.toString();

                    response.setContentType("application/json");
                    response.getWriter().write(responseData);
                } else {
                    System.out.println("Can't able to fetch the data: " + responseCode);
                    response.sendRedirect("login.jsp?error=" + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                // will redirect you back to the login page with the error message
                response.sendRedirect("login.jsp?error=" + e.getMessage());
            }
        } else {
            // will redirect you back to the login page with the error message
            response.sendRedirect("login.jsp?error=login_404");
        }
    }
}