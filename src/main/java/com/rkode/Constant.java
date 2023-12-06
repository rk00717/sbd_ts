package com.rkode;

public class Constant {
    public static final String LINK = "https://qa2.sunbasedata.com/sunbase/portal/api/";
    public static final String AUTH_API = LINK + "assignment_auth.jsp";
    public static final String CLIENT_API = LINK + "assignment.jsp";

    public static final String CMD = "?cmd=";

    public static final String CLIENT_INFO_API = CLIENT_API + CMD + "get_customer_list";
    public static final String CREATE_CLIENT_API = CLIENT_API + CMD + "create";
    public static final String UPDATE_CLIENT_API = CLIENT_API + CMD + "update&uuid=";
    public static final String REMOVE_CLIENT_API = CLIENT_API + CMD + "delete&uuid=";
}
