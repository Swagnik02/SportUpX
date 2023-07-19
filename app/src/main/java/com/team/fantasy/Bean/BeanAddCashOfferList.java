package com.team.fantasy.Bean;

import java.io.Serializable;

public class BeanAddCashOfferList implements Serializable {

    String  max_range,min_range,amount;



    public String getMax_range() {
        return max_range;
    }

    public void setMax_range(String max_range) {
        this.max_range = max_range;
    }

    public String getMin_range() {
        return min_range;
    }

    public void setMin_range(String min_range) {
        this.min_range = min_range;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
