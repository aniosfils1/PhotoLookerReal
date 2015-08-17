package com.example.myfirst.photolooker;

/**
 * Created by Myfirst on 8/17/2015.
 */
public class InstagramComment {
    public String comment;
    public String username;
    public String userPicUrl;

    public InstagramComment(String comment, String username, String userPicUrl) {
        this.comment = comment;
        this.username = username;
        this.userPicUrl = userPicUrl;
    }
}
