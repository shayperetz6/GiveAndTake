package com.android.giveandtake.home;

/**
 * Class to create a post with all it's information
 */
public class Post {
    private int imageResocure;
    private String nameAsk;
    private String phoneAsk;
    private String city;
    private String give;
    private String take;
    private String currentUserID;
    private String freeText;
    private String postid;
    private long time;
    private String hours;


    public Post(int img,
                String nameAsk,
                String phoneAsk,
                String City,
                String GiveAsk,
                String TakeAsk,
                String moreInfoText,
                String id,
                String postid,
                long timee,
                String hours) {
        imageResocure = img;
        this.nameAsk = nameAsk;
        this.phoneAsk = phoneAsk;
        give = GiveAsk;
        take = TakeAsk;
        currentUserID = id;
        freeText = moreInfoText;
        this.postid = postid;
        city = City;
        time = timee;
        this.hours = hours;
    }

    public String getHours() {
        return hours;
    }

    public void setNameAsk(String nameAsk) {
        this.nameAsk = nameAsk;
    }

    public void setPhoneAsk(String phoneAsk) {
        this.phoneAsk = phoneAsk;
    }

    public void setFreeText(String newFreeText) {
        freeText = newFreeText;
    }


    public int getImageResocure() {
        return imageResocure;
    }

    public String getNameAsk() {
        return nameAsk;
    }

    public String getPhoneAsk() {
        return phoneAsk;
    }

    public String getGive() {
        return give;
    }

    public String getTake() {
        return take;
    }

    public String getPostid() {
        return postid;
    }

    public String getCity() {
        return city;
    }

    public String getcurrentUserID() {
        return currentUserID;
    }

    public String getfreeText() {
        return freeText;
    }


    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() { return time; }

    public String GetDate() { return new java.text.SimpleDateFormat("dd/MM/yyyy").format(time); }
}


