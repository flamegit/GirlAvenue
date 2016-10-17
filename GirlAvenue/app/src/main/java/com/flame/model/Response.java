package com.flame.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/3.
 */
public class Response {

    private String error;
    private List<Girl> results;

    public List<Girl> getResults(){
        return results;
    }



    public static class Girl{
        public String  _id;
        String  createdAt;
        String  desc;
        String  publishedAt;
        String  source;
       // String  type;
        public String  url;
        boolean used;
        String who;
    }
}
