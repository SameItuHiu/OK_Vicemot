package com.example.e_vicemote.Model;

public class UserDetail {
    static String username = "";
    static String chatWith = "";
    static String namechatWith = "";

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserDetail.username = username;
    }

    public static String getChatWith() {
        return chatWith;
    }

    public static void setChatWith(String chatWith) {
        UserDetail.chatWith = chatWith;
    }

    public static String getNamechatWith() {
        return namechatWith;
    }

    public static void setNamechatWith(String namechatWith) {
        UserDetail.namechatWith = namechatWith;
    }
}
