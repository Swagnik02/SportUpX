package com.team.fantasy.APICallingPackage;


import com.team.fantasy.AppConfig;

public class Config {


    public static String SERVERURL = AppConfig.ApiUrl;

    //APK NAME
    public static String APKNAME = "";

    //APK URL
    public static String APKURL = "";

    public static String BASEURL = SERVERURL+"myrest/user/";

    public static String IMAGEBASEURL = SERVERURL+"uploads/";

    public static String ProfileIMAGEBASEURL = SERVERURL+"uploads/user/";

    public static String TEAMFLAGIMAGE = "";
    //public static String PLAYERIMAGE = IMAGEBASEURL+"player/";
    public static String PLAYERIMAGE = "";

    public static String BANNERIMAGE = IMAGEBASEURL+"offers/";

    public static String LEADERBOARDPLAYERIMAGE = IMAGEBASEURL+"leaderboard/";

    public static String TERMSANDCONDITIONSURL = SERVERURL+"terms-&-conditions";
    public static String FANTASYPOINTSYSTEMURL = SERVERURL+"points-system";
    public static String HOWTOPLAYURL = SERVERURL;
    public static String ABOUTUSURL = SERVERURL+"about-us";
    public static String HELPDESKURL = SERVERURL;
    public static String PRICING = SERVERURL;

    public static String REFUNDPOLICY = SERVERURL+"refund-policy";
    public static String LEGALITY = SERVERURL+"legality";
    public static String WITHDRAWPOLICY = SERVERURL+"withdraw-cash";


    public static String SIGNUP = BASEURL + "user_registration";
    public static String LOGIN = BASEURL + "login";
    public static String VERIFYOTP = BASEURL + "user_number_verify";
    public static String RESENDOTP = BASEURL + "resend_otp";

    public static String FORGOTPASSWORD = BASEURL + "forget_password";
    public static String VERIFYFORGOTPASSWORD = BASEURL + "varify_forgot_password";

    public static String UPDATENEWPASSWORD = BASEURL + "update_password";
    public static String CHANGEPASSWORD = BASEURL + "change_password";

    public static String HOMEFIXTURES = BASEURL + "match_record";
    public static String MYFIXTURES = BASEURL + "mymatch_record";
    public static String CONTESTLIST = BASEURL + "contest_list";
    public static String WINNINGINFOLIST = BASEURL + "winning_info";
    public static String PLAYERLIST = BASEURL + "team_list";
    public static String VIEWPROFILE = BASEURL + "view_profile";
    public static String EDITPROFILE = BASEURL + "edit_profile";

    public static String PLAYERINFO = BASEURL + "Player_information";

    public static String SAVETEAM = BASEURL + "save_team";
    public static String MYTEAMLIST = BASEURL + "my_team_list";

    public static String JOINCONTEST = BASEURL + "join_contest";

    public static String MYJOINCONTESTLIST = BASEURL + "my_join_contest_list";

    public static String MYFIXTURELEADERBOARD = BASEURL + "joined_contest";
    public static String MYFIXTURELEADERBOARDTEAM = BASEURL + "my_joined_team_list";

    /// MyLive Contest List
    public static String MYJOINLIVECONTESTLIST = BASEURL + "my_join_contest_list_live";

    //Live Leaderboard
    public static String MYLIVELEADERBOARD = BASEURL + "joined_contest";

    //Live Leaderboard Team Preview
    public static String MYLIVELEADERBOARDTEAM = BASEURL + "my_joined_team_list";

    /// MyResult Contest List
    public static String MYJOINRESULTCONTESTLIST = BASEURL + "my_join_contest_list_live";

    //MyAccount
    public static String MYACCOUNT = BASEURL + "my_account";

    //MyPlaying History
    public static String MYPLAYINGHISTORY = BASEURL + "playing_history";

    //AddMoney
    public static String ADDAMOUNT = BASEURL + "add_amount";

    public static String ADDAMOUNTOFFER = BASEURL + "bonus_offer";

    //MyTransactionList
    public static String MYTRANSACTIONLIST = BASEURL + "my_account_transaction";
    //Notification List
    public static String NOTIFICATIONLIST = BASEURL + "notification";

    //Invited Friends List
    public static String INVITEDFRIENDSLIST = BASEURL + "refer_friend_list";

    //Withdraw Amount User Data If Saved
    public static String WITHDRAWAMOUNTUSERDATA = BASEURL + "user_withdrow_information";

    //Submit Withdrawal Request
    public static String WITHDRAWLREQUEST = BASEURL + "withdrow_amount";

    //Global Ranking Request
    public static String GLOBALRANKINGLIST = BASEURL + "global_rank";

    //HashKeyRequest
    public static String HASHKEYREQUEST = BASEURL + "hashkey";

    //HomeBannerRequest
    public static String HOMEBANNER = BASEURL + "get_offers";

    //UploadDocument
    public static String UPLOADDOUCMENT = BASEURL + "update_documents";
    //UploadImage
    public static String UpdateUserProfileImage = BASEURL + "update_user_profile_image";
    //UpdateApp
    public static String UPDATEAPP = BASEURL + "update_app";

    //RankCreateContest
    public static String CREATECONTESTRANK = BASEURL + "user_contest";

    //CreateOwnContest
    public static String CREATEOWNCONTEST = BASEURL + "user_contestCreate";

    //CreateOwnContestList
    public static String CREATEOWNCONTESTLIST = BASEURL + "user_contestList";

    //TrakNPayPaymentGateway Verify Hash Link
    public static String VERIFYHASH = BASEURL + "verifyHash";

    //Cashfree Return Token Url
    public static String RETURNTOKEN = BASEURL + "CashFree_token";

    //Home Match Api
    public static String MYMATCHRECORD = BASEURL + "my_match_record";

    //paytm
    public static String FETCH_PAYTM_CREDENTIALS = BASEURL + "fetch_paytm_credentials";


}