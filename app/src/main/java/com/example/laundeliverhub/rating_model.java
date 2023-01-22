package com.example.laundeliverhub;

public class rating_model {
   public float myrate ,numrate;
   public String shopId  , datetime ,display_name , my_image, meidd ,discription, phoneNumber ;

    public rating_model() {
    }

    public rating_model(float myrate, float numrate, String shopId,
                        String datetime, String display_name, String my_image , String meidd, String discription,
                        String phoneNumber
    ) {
        this.myrate = myrate;
        this.numrate = numrate;
        this.shopId = shopId;
        this.datetime = datetime;
        this.display_name = display_name;
        this.my_image = my_image;
        this.meidd = meidd;
        this.discription = discription;
        this.phoneNumber = phoneNumber;
    }



    public float getMyrate() {
        return myrate;
    }

    public void setMyrate(float myrate) {
        this.myrate = myrate;
    }

    public float getNumrate() {
        return numrate;
    }

    public void setNumrate(float numrate) {
        this.numrate = numrate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getMy_image() {
        return my_image;
    }

    public void setMy_image(String my_image) {
        this.my_image = my_image;
    }

    public String getMeidd() {
        return meidd;
    }

    public void setMeidd(String meidd) {
        this.meidd = meidd;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
