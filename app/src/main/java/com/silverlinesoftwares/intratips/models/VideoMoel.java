package com.silverlinesoftwares.intratips.models;

public class VideoMoel {
    String title;
    String video_id;

    public VideoMoel(String title, String video_id) {
        this.title = title;
        this.video_id = video_id;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }


}
