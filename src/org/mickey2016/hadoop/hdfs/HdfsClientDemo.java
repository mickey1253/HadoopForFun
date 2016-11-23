package org.mickey2016.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Mickey on 10/6/2016.
 */
public class HdfsClientDemo {

    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init() throws Exception {
         conf = new Configuration();
         conf.set("fs.defaultFS", "hdfs://www.mickey01.com:9000");
        // hdfs-site.xml的优先级为： jar包中的xml < project 中的 xml < 代码中的 conf.set
        conf.set("dfs.replication", "5");

       // fs = FileSystem.get(conf);
        // 拿到一个文件系统操作的客户端实例对象
        //可以直接传入uri和用户身份；
        fs = FileSystem.get(new URI("hdfs://www.mickey01.com:9000"), conf, "hadoop");
    }

    @Test
    public void testUpload() throws Exception {
        Thread.sleep(5000);
        fs.copyFromLocalFile(new Path("E:/hadoopTest/MyHeartForYou.txt"), new Path("/MyHeartForYou_copy.txt"));
        fs.close();
    }

    @Test
    public void testDownlaod() throws IOException {
        fs.copyToLocalFile(new Path("/MyHeartForYou_copy.txt"), new Path("E:/"));
        fs.close();
    }

    /* 打印参数 */
    @Test
    public void testConf(){
        Iterator<Map.Entry<String, String>> it = conf.iterator();
        while(it.hasNext()){
            Map.Entry<String,String> ent = it.next();
            System.out.println(ent.getKey() + " : " + ent.getValue());
        }
    }

    /*可以级联创建多层目录*/
    @Test
    public void testMkdir() throws Exception {
        boolean mkdirs = fs.mkdirs(new Path("/testMkdir/aaa/bbb"));
        System.out.println(mkdirs);
    }

    /* 删除目录 */
    @Test
    public void testDelete() throws Exception {
        /*传递两个参数，第一个是路径，第二个表示要不要递归删除, 设为true时，
        会将/testMkdir/aaa下的子目录也删除*/
        boolean deleteDir = fs.delete(new Path("/testMkdir/aaa"), true);
        System.out.println(deleteDir);
    }

    /*只列出文件*/
    @Test
    public void testListFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while(listFiles.hasNext()){

            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println("File Name: " + fileStatus.getPath().getName());

            System.out.println("Block Size: " + fileStatus.getBlockSize());

            System.out.println("Onwer: " + fileStatus.getOwner());

            System.out.println("Permission: " + fileStatus.getPermission());

            System.out.println("Replication: " + fileStatus.getReplication());

            System.out.println("---------------");

            BlockLocation[] blockLocations = fileStatus.getBlockLocations();

            for (BlockLocation b: blockLocations) {

                // 块的起始偏移量
                System.out.println("First block from: " + b.getOffset());
                //块长度
                System.out.println("First block size is: " + b.getLength());
                //块所在的主机
                String[] datanodes = b.getHosts();
                for (String dn : datanodes) {
                    System.out.println("Block located at : " + dn + " datanode");
                }


            }

        }

    }


    /*列出所有的文件夹和文件*/
    @Test
    public void testListFileAndDirectory() throws IOException {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        for(FileStatus file : listStatus){

            System.out.println("Name: " + file.getPath().getName());

            System.out.println(file.isFile()? "file" : "directory");

            System.out.println("----------");


        }
    }

    public static void main(String[] args) throws Exception{

        FileSystem fs = null;

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://www.mickey01.com:9000");

        fs = FileSystem.get(conf);

        //Thread.sleep(5000);
        fs.copyFromLocalFile(new Path("E:/hadoopTest/MyHeartForYou.txt"), new Path("/MyHeartForYou_copy.txt"));
        fs.close();



    }

}
