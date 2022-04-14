package com.humaira.lecturenote;

public class Notes {
    String key = "";
    String course= "";
    String topic= "";
    String date= "";
    String description= "";

    public Notes(String key) {
    }

    public Notes(String key ,String course, String topic, String date, String description) {
        this.key = key;
        this.course = course;
        this.topic = topic;
        this.date = date;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
