package com.example.myfirst.photolooker;

import java.util.ArrayList;

/**
 * Created by Myfirst on 8/17/2015.
 */
public class InstagramPhoto {
    public String username;
    public String userPicUrl;
    public String caption;
    public String imageUrl;
    public String location;
    public int likeCount;
    public int imageHeight;
    public int imageWidth;
    public long createdTime;
    public int commentsCount;
    public ArrayList<InstagramComment> comments = new ArrayList<>();
}
