package com.example.Noam.myapplication.backend.core;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class PostInfo {

    private String id;
    private String tumblerId;
    private String title;
    private String content;
    private String tag;
    private byte[] img;


    public PostInfo(String id, String tumblerId, String title,
                    String content, String tag, byte[] img) {
        this.id = id;
        this.tumblerId = tumblerId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.img = img;
    }




    public PostInfo(String id){
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTumblerId() {
        return tumblerId;
    }

    public void setTumblerId(String tumblerId) {
        this.tumblerId = tumblerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public static String toJson(List<PostInfo> posts) {

        JSONObject json=new JSONObject();
        if (posts==null){
            return null;
        }
        if (posts.size()==0){
            return  null;
        }

        JSONArray jsonArray=new JSONArray();

        for(PostInfo postInfo:posts){
            JSONObject postJson= new JSONObject();
            postJson.put("id",postInfo.getId());
            postJson.put("title",postInfo.getTitle());
            postJson.put("content",postInfo.getContent());
            postJson.put("tumbler_id",postInfo.getTumblerId());
            postJson.put("tag",postInfo.getTag());
            jsonArray.add(postJson);

        }
        json.put("posts",jsonArray);
        return json.toString(2);
    }
}
