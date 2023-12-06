package com.rkode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Utils {
    // Will try to fetch the access_token from the session cookies
    public static String GetAccessToken(HttpServletRequest request) {
        String access_token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    access_token = cookie.getValue();
                    System.out.println("Cookie : " + access_token);
                    break;
                }
            }
        }
        return access_token;
    }

    // A util function to create client data base as json body
    public static String GetClientBody(String first_name, String last_name, String street, String address, String city,
            String state, String email, String phone) {
        return "{ \"first_name\":\"" + first_name + "\", \"last_name\":\"" + last_name
                + "\", \"street\":\"" + street + "\", \"address\":\"" + address + "\", \"city\":\"" + city
                + "\", \"state\":\"" + state + "\", \"email\":\"" + email + "\", \"phone\":\"" + phone + "\" }";
    }
}
