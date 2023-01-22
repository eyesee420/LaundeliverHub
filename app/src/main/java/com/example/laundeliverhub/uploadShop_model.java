package com.example.laundeliverhub;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class uploadShop_model implements Serializable {


    private String uri;
    private String shop_name,location_address;
    public int washdry1,washdry2,washdry3,washdry4,washdry5;
    public float myrate ;
    public int numrate;
    public String idd;
    public String phoneNumber , active_status;
    public int iron_fold , addwash , bedsheets_Comforters , blankets_curtains;

    public uploadShop_model() {
    super();
    }

    //constructor for add and get  shop
    public uploadShop_model(String uri, String shop_name, String location_address,String phoneNumber,
                            String active_status,
                            int washdry1, int washdry2, int washdry3, int washdry4,
                            int washdry5 , String idd, int iron_fold, int addwash, int bedsheets_Comforters,
                            int blankets_curtains ) {
        this.uri = uri;
        this.shop_name = shop_name;
        this.location_address = location_address;
        this.phoneNumber = phoneNumber;
        this.active_status = active_status;
        this.washdry1 = washdry1;
        this.washdry2 = washdry2;
        this.washdry3 = washdry3;
        this.washdry4 = washdry4;
        this.washdry5 = washdry5;
        this.idd = idd;
        this.iron_fold = iron_fold;
        this.addwash = addwash;
        this.bedsheets_Comforters = bedsheets_Comforters;
        this.blankets_curtains = blankets_curtains;


    }


//    //constructor for update and get shop
    public uploadShop_model(String pics, String shopname, String shoploc,String phoneNumber,
                            String active_status,
                            int w1, int w2, int w3, int w4, int w5, String shop_id,
                            float star_rate, int num_rate, int ironfold,int addwash, int bedsheets_Comforters,
                            int blankets_curtains) {

        this.uri = pics;
        this.shop_name = shopname;
        this.location_address = shoploc;
        this.phoneNumber = phoneNumber;
        this.active_status = active_status;
        this.washdry1 = w1;
        this.washdry2 = w2;
        this.washdry3 = w3;
        this.washdry4 = w4;
        this.washdry5 = w5;
        this.idd = shop_id;
        this.myrate = star_rate;
        this.numrate = num_rate;
        this.iron_fold = ironfold;
        this.addwash = addwash;
        this.bedsheets_Comforters = bedsheets_Comforters;
        this.blankets_curtains = blankets_curtains;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public int getWashdry1() {
        return washdry1;
    }

    public void setWashdry1(int washdry1) {
        this.washdry1 = washdry1;
    }

    public int getWashdry2() {
        return washdry2;
    }

    public void setWashdry2(int washdry2) {
        this.washdry2 = washdry2;
    }

    public int getWashdry3() {
        return washdry3;
    }

    public void setWashdry3(int washdry3) {
        this.washdry3 = washdry3;
    }

    public int getWashdry4() {
        return washdry4;
    }

    public void setWashdry4(int washdry4) {
        this.washdry4 = washdry4;
    }

    public int getWashdry5() {
        return washdry5;
    }

    public void setWashdry5(int washdry5) {
        this.washdry5 = washdry5;
    }

    public float getMyrate() {
        return myrate;
    }

    public void setMyrate(float myrate) {
        this.myrate = myrate;
    }

    public int getNumrate() {
        return numrate;
    }

    public void setNumrate(int numrate) {
        this.numrate = numrate;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public int getIron_fold() {
        return iron_fold;
    }

    public void setIron_fold(int iron_fold) {
        this.iron_fold = iron_fold;
    }

    public int getAddwash() {
        return addwash;
    }

    public void setAddwash(int addwash) {
        this.addwash = addwash;
    }

    public int getBedsheets_Comforters() {
        return bedsheets_Comforters;
    }

    public void setBedsheets_Comforters(int bedsheets_Comforters) {
        this.bedsheets_Comforters = bedsheets_Comforters;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBlankets_curtains() {
        return blankets_curtains;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }

    public void setBlankets_curtains(int blankets_curtains) {
        this.blankets_curtains = blankets_curtains;

    }
}
