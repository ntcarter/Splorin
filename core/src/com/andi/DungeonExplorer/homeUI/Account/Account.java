package com.andi.DungeonExplorer.homeUI.Account;

/**
 * Created by Ntcarter on 11/7/2017.
 */

/**
 * This class keeps track of all the user information once the user has logged in (methods should be self explanatory)
 */
public class Account {

    private String userName;
    private String userPassword;
    private int userID;
    private int adminID;

    public Account(){
        userName = "";
        userPassword = "";
        userID = -1;
        adminID = 0;
    }

    public void SetUserName(String name){
        userName = name;
    }

    public void SetUserPassword(String password){
        userPassword = password;
    }

    public void SetUserID(int id){
        userID = id;
    }

    public void SetAdminID(int id){
        adminID = id;
    }

    public void SetFullAccount(String name, String password, int id, int aID){
        userName = name;
        userPassword = password;
        userID = id;
        adminID = id;
    }

    public String GetUserName(){
        return userName;
    }

    public String GetUserPassword(){
        return userPassword;
    }

    public void LogOut(){
        userName = "";
        userPassword = "";
        userID = -1;
        adminID = 0;
    }

    public int GetUserID(){
        return userID;
    }

    public int GetAdminID(){
        return  adminID;
    }
}
