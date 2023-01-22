package com.example.laundeliverhub;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class addOns_Prices implements Serializable {
    public String uri;
    public int kilo_price;
    public int detergent_quantity;
    public int softener_quantity;
    public int bleach_quantity;
    public int iron_on;
    public int total_price;
    public String discriptions ,shop_name,location_address;
    public String mTimestamp;
    public String shop_id;
    public int addwash , bedsheets_Comforters , blankets_curtains;
    public String owners_phonenumber;
    public addOns_Prices() {
    }

    public addOns_Prices(String uri,int kilo_price, int detergent_quantity, int softener_quantity, int bleach_quantity,
                         int iron_on, int total_price, String discriptions, String shop_name, String location_address,
                         String mtimestamp ,String shop_id, int addwash,int bedsheets_Comforters,int blankets_curtains
            ,String owners_phonenumber) {
        this.uri = uri;
        this.kilo_price = kilo_price;
        this.detergent_quantity = detergent_quantity;
        this.softener_quantity = softener_quantity;
        this.bleach_quantity = bleach_quantity;
        this.iron_on = iron_on;
        this.total_price = total_price;
        this.discriptions = discriptions;
        this.shop_name = shop_name;
        this.location_address = location_address;
        this.mTimestamp = mtimestamp;
        this.shop_id = shop_id;
        this.addwash = addwash;
        this.bedsheets_Comforters = bedsheets_Comforters;
        this.blankets_curtains = blankets_curtains;
        this.owners_phonenumber = owners_phonenumber;

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getKilo_price() {
        return kilo_price;
    }

    public void setKilo_price(int kilo_price) {
        this.kilo_price = kilo_price;
    }

    public int getDetergent_quantity() {
        return detergent_quantity;
    }

    public void setDetergent_quantity(int detergent_quantity) {
        this.detergent_quantity = detergent_quantity;
    }

    public int getSoftener_quantity() {
        return softener_quantity;
    }

    public void setSoftener_quantity(int softener_quantity) {
        this.softener_quantity = softener_quantity;
    }

    public int getBleach_quantity() {
        return bleach_quantity;
    }

    public void setBleach_quantity(int bleach_quantity) {
        this.bleach_quantity = bleach_quantity;
    }

    public int getIron_on() {
        return iron_on;
    }

    public void setIron_on(int iron_on) {
        this.iron_on = iron_on;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getDiscriptions() {
        return discriptions;
    }

    public void setDiscriptions(String discriptions) {
        this.discriptions = discriptions;
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

    public void setLocation_address(String location_address) {this.location_address = location_address;
    }
    public String getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
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

    public int getBlankets_curtains() {
        return blankets_curtains;
    }

    public void setBlankets_curtains(int blankets_curtains) {
        this.blankets_curtains = blankets_curtains;
    }
    public String getOwners_phonenumber() {
        return owners_phonenumber;
    }

    public void setOwners_phonenumber(String owners_phonenumber) {
        this.owners_phonenumber = owners_phonenumber;
    }
}
