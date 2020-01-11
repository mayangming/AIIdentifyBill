package com.aidiscern.bill;

public class User extends Parent{
    private String userName = "";
    private int age = 0;

    public User(String userName) {
        super(userName);
    }

    public User(int age) {
        super(age);
        this.userName = userName;
    }
}