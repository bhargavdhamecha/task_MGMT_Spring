package com.task.management.shared.constant;

public final class ApiConstant {

    // Prevent instantiation
    private ApiConstant(){}

    public static final String BASE_PATH = "";

    // controller url
    public static final String AUTH_CONTROLLER = "auth";
    public static final String ORG_CONTROLLER = "org";
    public static final String PROJECT_CONTROLLER = "project";

    //api url
    public static final String CHECK_USERNAME = "/check-username";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SIGNUP = "/signup";
    public static final String CREATE_PROJECT = "/create-project";
    public static final String REGISTER_ORG = "/register-org";

}
