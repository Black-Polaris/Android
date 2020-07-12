package com.example.task;

public class ChatMessage {


    private String name;// 姓名
    private String message;// 消息
    private Type type;// 类型：0.发送者 1.接受者

    public ChatMessage() {

    }

    public ChatMessage(String message, Type type) {
        super();
        this.message = message;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public enum Type {
        INCOUNT, OUTCOUNT
    }


}