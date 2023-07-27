package com.team.fantasy.utils;

//public class PaytmConstants {
//    public static String MerchantKEY ="You Mearchant Key";
//    public static String M_ID ="Your MID";
//    //For Staging Use below URL
//    public static String CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
//    //For Production use Below URL
//    //public static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
//    public static String INDUSTRY_TYPE_ID ="INDUSTRY_TYPE_ID";
//    public static String CHANNEL_ID ="CHANNEL_ID";
//    public static String WEBSITE ="WEBSITE";
//    public  static  String CHECKTRANSACTION = "https://securegw-stage.paytm.in/merchant-status/getTxnStatus";
//}
public class PaytmConstants {
    public static String MerchantKEY = ""; // Initialize it as an empty string
    public static String M_ID = ""; // Initialize it as an empty string

    // Fetch the CALLBACK_URL from the database as well, or you can keep it as before
    // For Staging Use below URL
    public static String CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";
    // For Production use Below URL
    // public static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";

    public static String INDUSTRY_TYPE_ID = ""; // Initialize it as an empty string
    public static String CHANNEL_ID = ""; // Initialize it as an empty string
    public static String WEBSITE = ""; // Initialize it as an empty string
    public static String CHECKTRANSACTION = "https://securegw-stage.paytm.in/merchant-status/getTxnStatus";
}
