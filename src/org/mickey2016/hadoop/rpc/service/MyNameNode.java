package org.mickey2016.hadoop.rpc.service;

import org.mickey2016.hadoop.rpc.protocol.ClientNamenodeProtocol;

/**
 * Created by Mickey on 10/17/2016.
 */
public class MyNameNode implements ClientNamenodeProtocol{

    /*模拟namenode的业务方法之一，查询元数据
    * */
    @Override
    public String getMetaData(String path){
        return path + " : 3 - {BLK_1, BLK_2} ...";

    }
}
