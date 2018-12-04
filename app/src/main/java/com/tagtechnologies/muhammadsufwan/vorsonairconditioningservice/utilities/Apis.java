package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities;
/*******************************************************/
/* Created by muhammad.sufwan on 5/7/2018 user email   */
/*******************************************************/
public class Apis {

    //public static final String BASE_URL = "http://acappapi.vorson.net/";
    public static final String BASE_URL = "http://192.168.0.47/vorson_android/";

//    LOGIN CLIENT REGISTER URL
    public static final String URL_CLIENT_REGISTER = BASE_URL + "vorson_login.php?apicall=register";

//    LOGIN CLIENT LOGIN URL
    public static final String URL_CLIENT_LOGIN = BASE_URL + "vorsonUserLogin.php";

//    SUBMIT TICKETS
    public static final String URL_TICKET = BASE_URL + "submit_ticket.php";

//    GET TICKET HELP LIST
    public static final String URL_GET_HELP_LIST = BASE_URL + "getHelpList.php";

//    PAID AND UNPAID INVOICES
    public static final String URL_FOR_SERVICE_PAID_LIST = BASE_URL + "get_invoice_list.php";

//    FOR GETTING LIST AND PURCHASE URLS
    public static final String URL_FOR_SUBMIT_ORDER_AC = BASE_URL + "submit_new_ac_order.php";

//    FOR GETTING NEW AC LIST URLS
    public static final String URL_FOR_NEW_AC_DATA = BASE_URL + "get_ac_selection_data.php";
    public static final String URL_FOR_MODEL = BASE_URL + "get_new_selected_ac.php";

//    TECHNICIAN URLS
    public static final String TECHNICIAN_LOGIN = BASE_URL + "TechVorsonLogin.php";

//    CREATE JOB AND SUBMIT URLS
    public static final String JOB_SUBMIT_FORM_URL = BASE_URL + "create_job_api.php";

//    ASSIGN JOB LIST URL
    public static final String ASS_JOB_LIST_URL = BASE_URL + "assign_job_list.php";

//    SET STATUS FOR JOB ASSIGN TO TECHNICIAN
    public static final String SET_STATUS_URL = BASE_URL + "set_job_status.php";

//    GET INVOICE DATA
    public static final String URL_FOR_GET_INVOICE_DATA = BASE_URL + "get_invoice_data.php";

//    GET JOB LIST WAS MADE FROM TECHNICIAN
    public static final String GET_CREATE_JOB_LIST_URL = BASE_URL + "get_job_list.php";
    public static final String SET_TECH_HELP_URL = BASE_URL + "technician_help_req.php";
    public static final String TECHNI_GET_TIME_URL = BASE_URL + "get_technician_workTime.php";
    public static final String URL_CHANGE_TECH_PASS = BASE_URL + "change_technician_pass.php";
    public static final String URL_CHANGE_CLIENT_PASS = BASE_URL + "change_client_pass.php";
    public static final String URL_CHANGE_TECH_PROFILE = BASE_URL + "change_technician_profile.php";
    public static final String URL_CHANGE_CLIENT_PROFILE = BASE_URL + "change_client_profile.php";
    public static final String URL_GET_CLIENT_PROFILE = BASE_URL + "get_client_profile_data.php";
    public static final String URL_GET_CLIENT_TICKET_LIST = BASE_URL + "get_client_ticket_list.php";



}
