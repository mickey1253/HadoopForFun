package com.mickey.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.util.Progressable;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Mickey on 7/17/2016.
 */
public class HdfsUtilLocal {

    FileSystem fs = null;

    @Before
    public  void init() throws Exception{

        //Before run the project, set the configuration "VM Options" to  "-DHADOOP_USER_NAME=hadoop"

        System.setProperty("hadoop.home.dir", "E:/MickeyHaoopStudy/Software/Hadoop/hadoop-2.6.4");

        Configuration conf = new Configuration();

      //  fs = FileSystem.get(conf);

        //FileSystem.get(URI, Configuration, UserName)
        fs = FileSystem.get(new URI("hdfs://ns1/"), conf, "hadoop");
    }

    @Test
    public void uploard() throws IOException {

        /**
         * Set Hadoop home
         */

        Path dst = new Path("hdfs://ns1/aa/MyHeartForYou1.txt");

        FSDataOutputStream os = fs.create(dst);

        FileInputStream in = new FileInputStream("E:/hadoopTest/MyHeartForYou.txt");

        IOUtils.copy(in, os);
    }

    @Test
    public  void upload2() throws IOException{

        fs.copyFromLocalFile(new Path("E:/hadoopTest/MyHeartForYou.txt"), new Path("hdfs://ns1/aaa/bbb/MyHeartForYou2.txt"));

    }

    @Test
    public void download() throws  IOException{

        fs.copyToLocalFile(new Path("hdfs://ns1/aa/MyHeartForYou2.txt"), new Path("E:/hadoopTest/MyHeartForYou3.txt"));

    }

    @Test
    public void listFiles() throws IOException {

    RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);

        while(files.hasNext()){
            LocatedFileStatus file = files.next();
            Path filePath = file.getPath();
            String fileName = filePath.getName();
            System.out.println(fileName);
        }

        System.out.println("===========");

        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        for (FileStatus status : listStatus ) {
            String fileStatus = status.getPath().getName();
            System.out.println(fileStatus + (status.isDirectory() ? " is a directory" : " is a file"));
        }

    }

    @Test
    public void mkdir() throws IOException {

        fs.mkdirs(new Path("/aaa/bbb/ccc"));

    }

    @Test
    public void rm() throws IOException {

        fs.delete(new Path("/aa"), true);

    }

}
