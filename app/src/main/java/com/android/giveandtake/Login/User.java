package com.android.giveandtake.Login;

/**
 * Class that represents all of a user's information to add it in firebase
 */
public class User {

    public String name, email, phone,city;

    public User(){
    }


    public User(String name, String email, String phone,String city) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city=city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }

    public void setUserPhone(String userPhone) {
        this.phone = phone;
    }

}


