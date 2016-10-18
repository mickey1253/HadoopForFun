package org.mickey2016.hadoop.rpc.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.mickey2016.hadoop.rpc.protocol.IUserLoginService;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Mickey on 10/17/2016.
 */
public class UserLoginAction {

    public static void main(String[] args) throws IOException {

     IUserLoginService userLoginService = RPC.getProxy(IUserLoginService.class, 100L, new InetSocketAddress("localhost", 9999), new Configuration());

        String login =  userLoginService.login("Neal Caffery", "123456");

        System.out.println(login);
    }

}
