package com.mickey.hadoop.rpc;

/**
 * Created by Mickey on 7/20/2016.
 */
public class LoginServiceImpl implements LoginServiceInterface {

    @Override
    public String login(String username, String password) {
        return username + " logged in successfully!";
    }
}
