package com.android.giveandtake.History;

public class History {

    private int imageResocure;
    private String name_userPost;
    private String name_userTrade;
    private String history_giveOption;
    private String history_takeOption;
    private String current_historyId;
    private String user_postedID;
    private long history_time;
    private String TextReason;

    public long getHistory_time() {
        return history_time;
    }

    public void setHistory_time(long history_time) {
       this.history_time=history_time;
    }

    public String History_timeAndDate() {
        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(history_time);
    }

    public String getUser_postedID() {
        return user_postedID;
    }

    public String getCurrent_historyId() {
        return current_historyId;
    }

    public String getTextReason() {
        return TextReason;
    }

    public int getImageResocure() {
        return imageResocure;
    }

    public String getName_userPost() {
        return name_userPost;
    }

    public String getName_userTrade() {
        return name_userTrade;
    }

    public String getHistory_giveOption() {
        return history_giveOption;
    }

    public String getHistory_takeOption() {
        return history_takeOption;
    }


    public History(int imageResocure, String name_userPost, String name_userTrade, String history_giveOption, String history_takeOption, String current_historyId, String user_postedID,long time,String textReason) {
        this.imageResocure = imageResocure;
        this.name_userPost = name_userPost;
        this.name_userTrade = name_userTrade;
        this.history_giveOption = history_giveOption;
        this.history_takeOption = history_takeOption;
        this.current_historyId = current_historyId;
        this.user_postedID = user_postedID;
        this.history_time = time;
        this.TextReason = textReason;
    }

}
