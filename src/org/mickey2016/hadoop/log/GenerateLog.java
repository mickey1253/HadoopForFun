package org.mickey2016.hadoop.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by Mickey on 10/19/2016.
 */
public class GenerateLog {
    public static void main(String[] args) throws Exception {

        Logger logger = LogManager.getLogger("testlog");
        int i = 0;
        while(true){
            logger.info(new Date().toString() + "----------------");
            i++;
            // Thread.sleep(500);
            if ( i > 1000000)
                break;
        }

    }
}
