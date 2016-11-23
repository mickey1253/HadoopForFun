package com.mickey.hadoop.rpc;

/**
 * Created by Mickey on 7/20/2016.
 */
public interface LoginServiceInterface {

    public static final long versionID = 1L;

    public String login(String username, String password);
}
