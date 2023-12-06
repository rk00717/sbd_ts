package com.rkode.operations;

import com.rkode.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth_user")
public class AuthenticateClient extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetching parameters for the authentication
        String username = request.getParameter("id_input");
        String password = request.getParameter("pwd_input");

        // Setting up the connections
        URL url = new URL(Constant.AUTH_API);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/raw");
        connection.setDoOutput(true);

        // Writing the data for the packet
        String jsonInputString = "{ \"login_id\": \"" + username + "\", \"password\":\"" + password + "\" }";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        // Trying to read the response
        StringBuilder strResponse;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine;
            strResponse = new StringBuilder();
            while ((responseLine = br.readLine()) != null) {
                strResponse.append(responseLine.trim());
            }

            // On Success will fetch the Access token and store it as session cookie
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String access_token = strResponse.toString().split(":")[1].trim().replaceAll("\"", "").replace("}", "");
                Cookie tokenCookie = new Cookie("access_token", access_token);
                tokenCookie.setPath("/");
                System.out.println("Access Token : " + access_token);
                response.addCookie(tokenCookie);

                // will redirect to the page where you can see all the clients
                response.sendRedirect("/users_list.jsp");
            } else {
                // will redirect you back to the login page with the error message
                response.sendRedirect("login.jsp?error=invalid-authentication-code");
            }
        } catch (IOException exception) {
            // will redirect you back to the login page with the error message
            response.sendRedirect("login.jsp?error=invalid-authentication-code");
        }
    }
}