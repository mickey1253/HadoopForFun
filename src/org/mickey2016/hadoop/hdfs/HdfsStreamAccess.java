package org.mickey2016.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Mickey on 10/13/2016.
 */
public class HdfsStreamAccess {

    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init() throws Exception {
        conf = new Configuration();
        // 拿到一个文件系统操作的客户端实例对象
        fs = FileSystem.get(conf);

        //可以直接传入uri和用户身份；
        fs = FileSystem.get(new URI("hdfs://www.mickey01.com:9000"), conf, "hadoop");
    }

    /*通过流的方式上传文件*/
    @Test
    public void testUpload() throws IOException {
        //True 表示覆盖已有文件
        FSDataOutputStream outputStream = fs.create(new Path("/NearCaffery.txt"), true);

        FileInputStream inputStream = new FileInputStream("E:/hadoopTest/NearCaffery.txt");

        IOUtils.copy(inputStream, outputStream);

    }


    /*流的方式获取文件*/
    @Test
    public void testDownload() throws IOException {
        FSDataInputStream inputStream = fs.open(new Path("/NearCaffery.txt"));

        FileOutputStream outputStream = new FileOutputStream("E:/hadoopTest/NearCaffery.copy");

        IOUtils.copy(inputStream, outputStream);

        IOUtils.copy(inputStream, System.out);

        //hadoop自己封装的方法，可以将文本打印到屏幕上
        // org.apache.hadoop.io.IOUtils.copyBytes(inputStream, System.out, 1024);
    }


    /*采用流的方式读取部分文件*/
    @Test
    public void testRandomAccess() throws IOException {

        FSDataInputStream inputStream = fs.open(new Path("/NearCaffery.txt"));
        /*seek方法表示定位到第8个字节*/
        inputStream.seek(8);
        FileOutputStream outputStream = new FileOutputStream("E:/hadoopTest/NearCaffery.copy.part2");

        // IOUtils.copyLarge(inputStream, outputStream);
        IOUtils.copy(inputStream, outputStream);

    }




}
