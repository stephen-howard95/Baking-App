package com.example.bakingapp;

import java.io.Serializable;

public class Step implements Serializable {
    private int stepId;
    private String shortDescription;
    private String fullDescription;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(int stepId, String shortDescription, String fullDescription, String videoUrl, String thumbnailUrl){
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getStepId(){
        return stepId;
    }
    public String getShortDescription(){
        return shortDescription;
    }
    public String getFullDescription(){
        return fullDescription;
    }
    public String getVideoUrl(){
        if(videoUrl.equals("")){
            return thumbnailUrl;
        }else{
            return videoUrl;
        }
    }
    public String getThumbnailUrl(){
        return thumbnailUrl;
    }
}
