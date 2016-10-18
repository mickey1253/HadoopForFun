package org.mickey2016.hadoop.rpc.protocol;

/**
 * Created by Mickey on 10/17/2016.
 */
public interface ClientNamenodeProtocol {

    public static final long versionID = 1L;

    public String getMetaData(String path);
}
