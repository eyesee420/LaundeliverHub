package com.example.laundeliverhub;

import java.io.Serializable;

public class Create_Users_Model implements Serializable {
    public String fullname ,phonenumber,currentaddress,emailaddress ;
    public boolean is_user  ;
    public boolean is_admin ;
    public boolean is_shopowner ;




    public Create_Users_Model() {
    }

//add
    public Create_Users_Model(String fullname, String phonenumber, String currentaddress, String emailaddress,
                              boolean is_user, boolean is_admin, boolean is_shopowner) {
        this.fullname = fullname;
        this.currentaddress = currentaddress;
        this.emailaddress = emailaddress;
        this.phonenumber = phonenumber;
        this.is_user = is_user;
        this.is_admin = is_admin;
        this.is_shopowner = is_shopowner;
    }

    public Create_Users_Model(String fullname, String phonenumber, String currentaddress) {

        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.currentaddress = currentaddress;

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

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public boolean isIs_user() {
        return is_user;
    }

    public void setIs_user(boolean is_user) {
        this.is_user = is_user;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isIs_shopowner() {
        return is_shopowner;
    }

    public void setIs_shopowner(boolean is_shopowner) {
        this.is_shopowner = is_shopowner;
    }
}

