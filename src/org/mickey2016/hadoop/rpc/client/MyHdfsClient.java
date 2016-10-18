package org.mickey2016.hadoop.rpc.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.mickey2016.hadoop.rpc.protocol.ClientNamenodeProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Mickey on 10/17/2016.
 */
public class MyHdfsClient {

    public static void main(String[] args ) throws IOException {

        ClientNamenodeProtocol namenode = RPC.getProxy(ClientNamenodeProtocol.class, 1L, new InetSocketAddress("localhost", 8888), new Configuration());
        String metaData = namenode.getMetaData("/NealCaffery");
        System.out.println(metaData);

    }

}
