package com.team.fantasy.utils;



public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {
            return "yourKey";
        }

        @Override
        public String merchant_ID() {
            return "yourMID";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "yourSalt";
        }

        @Override
        public boolean debug() {
            return true;
        }
    },

    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "yourKey";
        }  //O15vkB

        @Override
        public String merchant_ID() {
            return "yourMID";
        }   //4819816

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "yourSalt";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}
