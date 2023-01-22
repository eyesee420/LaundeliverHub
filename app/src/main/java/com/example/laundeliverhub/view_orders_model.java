package com.example.laundeliverhub;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class view_orders_model implements Serializable {
    public String uri ;
    public String payments ;
    public String pick_up_delivery ,status ;
    public String shop_name,location_address;
    public String fullname ,phonenumber,currentaddress,disriptions;
    public String mtimestamp;
    public int  kilo_price,detergent_quantity,softener_quantity,bleach_quantity,iron_on,delivery_fee ,total_price;
    public String idd ;
    public String user_idd;
    public String shop_id ;
    public String del_time;
    public int addwash , bedsheets_Comforters , blankets_curtains ;
    public String owners_phonenumber;
    public String status1,status2,status3,status4,status5;
    public String current_time1,current_time2,current_time3,current_time4,current_time5;
    public view_orders_model() {
        super();
    }


    //gcash
    public view_orders_model(String uri, String payments, String pick_up_delivery, String status, String shop_name,
                             String location_address, String fullname, String phonenumber, String currentaddress,
                             String disriptions, String mtimestamp, int kilo_price, int detergent_quantity, int softener_quantity,
                             int bleach_quantity, int iron_on, int delivery_fee, int total_price , String idd ,
                             String user_idd , String shop_id ,String del_time) {

        this.uri = uri;
        this.payments = payments;
        this.pick_up_delivery = pick_up_delivery;
        this.status = status;
        this.shop_name = shop_name;
        this.location_address = location_address;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.currentaddress = currentaddress;
        this.disriptions = disriptions;
        this.mtimestamp = mtimestamp;
        this.kilo_price = kilo_price;
        this.detergent_quantity = detergent_quantity;
        this.softener_quantity = softener_quantity;
        this.bleach_quantity = bleach_quantity;
        this.iron_on = iron_on;
        this.delivery_fee = delivery_fee;
        this.total_price = total_price;
        this.idd = idd;
        this.user_idd = user_idd;
        this.shop_id = shop_id;
        this.del_time = del_time;
    }

    ///cash on delivery
    public view_orders_model(String payments, String pick_up_delivery, String status, String shop_name,
                             String location_address, String fullname, String phonenumber, String currentaddress,
                             String disriptions, String mtimestamp, int kilo_price, int detergent_quantity, int softener_quantity,
                             int bleach_quantity, int iron_on, int delivery_fee, int total_price,String idd
            ,String user_idd,String shop_id ,String del_time ,int addwash,int bedsheets_Comforters,
                             int blankets_curtains ,String owners_phonenumber ,String status1
            ,String status2,String status3,String status4,String status5,String current_time1
            ,String current_time2 ,String current_time3,String current_time4 ,String current_time5) {


        this.payments = payments;
        this.pick_up_delivery = pick_up_delivery;
        this.status = status;
        this.shop_name = shop_name;
        this.location_address = location_address;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.currentaddress = currentaddress;
        this.disriptions = disriptions;
        this.mtimestamp = mtimestamp;
        this.kilo_price = kilo_price;
        this.detergent_quantity = detergent_quantity;
        this.softener_quantity = softener_quantity;
        this.bleach_quantity = bleach_quantity;
        this.iron_on = iron_on;
        this.delivery_fee = delivery_fee;
        this.total_price = total_price;
        this.idd = idd;
        this.user_idd = user_idd;
        this.shop_id = shop_id;
        this.del_time = del_time;
        this.addwash = addwash;
        this.bedsheets_Comforters = bedsheets_Comforters;
        this.blankets_curtains = blankets_curtains;
        this.owners_phonenumber = owners_phonenumber;
        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
        this.status4 = status4;
        this.status5 = status5;
        this.current_time1 = current_time1;
        this.current_time2 = current_time2;
        this.current_time3 = current_time3;
        this.current_time4 = current_time4;
        this.current_time5 = current_time5;

    }

    //getters and setters
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getPick_up_delivery() {
        return pick_up_delivery;
    }

    public void setPick_up_delivery(String pick_up_delivery) {
        this.pick_up_delivery = pick_up_delivery;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCurrentaddress() {
        return currentaddress;
    }

    public void setCurrentaddress(String currentaddress) {
        this.currentaddress = currentaddress;
    }

    public String getDisriptions() {
        return disriptions;
    }

    public void setDisriptions(String disriptions) {
        this.disriptions = disriptions;
    }

    public String getMtimestamp() {
        return mtimestamp;
    }

    public void setMtimestamp(String mtimestamp) {
        this.mtimestamp = mtimestamp;
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

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }


    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getUser_idd() {
        return user_idd;
    }

    public void setUser_idd(String user_idd) {
        this.user_idd = user_idd;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getDel_time() {
        return del_time;
    }

    public void setDel_time(String del_time) {
        this.del_time = del_time;
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

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getStatus3() {
        return status3;
    }

    public void setStatus3(String status3) {
        this.status3 = status3;
    }

    public String getStatus4() {
        return status4;
    }

    public void setStatus4(String status4) {
        this.status4 = status4;
    }

    public String getStatus5() {
        return status5;
    }

    public void setStatus5(String status5) {
        this.status5 = status5;
    }

    public String getCurrent_time1() {
        return current_time1;
    }

    public void setCurrent_time1(String current_time1) {
        this.current_time1 = current_time1;
    }

    public String getCurrent_time2() {
        return current_time2;
    }

    public void setCurrent_time2(String current_time2) {
        this.current_time2 = current_time2;
    }

    public String getCurrent_time3() {
        return current_time3;
    }

    public void setCurrent_time3(String current_time3) {
        this.current_time3 = current_time3;
    }

    public String getCurrent_time4() {
        return current_time4;
    }

    public void setCurrent_time4(String current_time4) {
        this.current_time4 = current_time4;
    }

    public String getCurrent_time5() {
        return current_time5;
    }

    public void setCurrent_time5(String current_time5) {
        this.current_time5 = current_time5;
    }
}
