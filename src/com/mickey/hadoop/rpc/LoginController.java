package com.mickey.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Mickey on 7/19/2016.
 */
public class LoginController {

    public static void main(String[] args) throws IOException {
        LoginServiceInterface proxy = RPC.getProxy(LoginServiceInterface.class,
                1L, new InetSocketAddress("www.mickey01.com", 10000),
                new Configuration());

        String result = proxy.login("Mickey", "12345678");

        System.out.println("Result is: " + result);


    }
}
