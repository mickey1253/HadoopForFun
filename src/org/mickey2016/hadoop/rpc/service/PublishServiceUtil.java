package org.mickey2016.hadoop.rpc.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC.*;
import org.mickey2016.hadoop.rpc.protocol.ClientNamenodeProtocol;
import org.mickey2016.hadoop.rpc.protocol.IUserLoginService;

import java.io.IOException;

/**
 * Created by Mickey on 10/17/2016.
 */
public class PublishServiceUtil  {

    public static void main(String[] args) throws IOException {
        Builder builder = new Builder(new Configuration());
        builder.setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(ClientNamenodeProtocol.class)
                .setInstance(new MyNameNode());

        Server server = builder.build();

        server.start();

        Builder builder2 = new Builder(new Configuration());
        builder2.setBindAddress("localhost")
                .setPort(9999)
                .setProtocol(IUserLoginService.class)
                .setInstance(new UserLoginServiceImpl());

        Server server2 = builder2.build();

        server2.start();


    }
}
