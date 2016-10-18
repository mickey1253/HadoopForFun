package org.mickey2016.hadoop.rpc.service;

import org.mickey2016.hadoop.rpc.protocol.IUserLoginService;

/**
 * Created by Mickey on 10/17/2016.
 */
public class UserLoginServiceImpl implements IUserLoginService {

    @Override
    public String login(String name, String password) {
        return name + " logged in successfully...";
    }
}
