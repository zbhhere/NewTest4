package com.example.zbh.newtest4.UserName;

/**
 * Created by zbh on 2017/6/27.
 */

public class UserName {
    static String username;
    static String password;

    public static int getFlag() {
        return Flag;
    }

    public static void setFlag(int flag) {
        Flag = flag;
    }

    static int Flag=0;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserName.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserName.username = username;
    }
}
