package com.android.giveandtake.Admin;


public class User_Admin_Class {
    public String name, phone,email;

    public User_Admin_Class(String name , String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;

            }
public User_Admin_Class(){
    this.name = "";
    this.phone = "" ;
    this.email="";
}

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }


    public String getUserPhone() {
        return phone;
    }

    public void setUserPhone(String userPhone) {
        this.phone = phone;
    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String Email) {
        this.email = Email;
    }


    ////setters and getters for Option1'2'3
}


