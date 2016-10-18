package org.mickey2016.hadoop.rpc.protocol;

/**
 * Created by Mickey on 10/17/2016.
 */
public interface IUserLoginService {

    public static final long versionID = 100L;

    public String login(String name, String password);


}
