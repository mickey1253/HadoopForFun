package com.mickey.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mickey on 7/13/2016.
 */
public class HdfsUtil {

    public static void main(String[] args) throws IOException {

        // Upload a file to HDFS

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        Path src = new Path("hdfs://ns1/jdk-8u91-linux-x64.tar.gzip");

        FSDataInputStream in = fs.open(src);

        FileOutputStream os = new FileOutputStream("E:/hadoopTest/jdk-1.8");

        IOUtils.copy(in, os);

//       fs.copyToLocalFile(new Path("hdfs://ns1/jdk-8u91-linux-x64.tar.gzip"), new Path("E:/hadoopTest/jdk-1.8"));

    }

}
