package com.example.topic.RoomInfo;

public class MessagePost {

    public String msg_name;
    public String msg_data;
    public String msg_time;

    public MessagePost(String name, String data, String time){
        this.msg_data = data;
        this.msg_name = name;
        this.msg_time = time;
    }

    public String getMsg_data() {
        return msg_data;
    }

    public String getMsg_name() {
        return msg_name;
    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_data(String msg_data) {
        this.msg_data = msg_data;
    }

    public void setMsg_name(String msg_name) {
        this.msg_name = msg_name;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }
}
