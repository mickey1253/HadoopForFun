package org.mickey2016.hadoop.mr.wcdemo;

/**
 * Created by Mickey on 10/24/2016.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 相当于一个yarn集群到客户端
 * 需要在此封装我们到 mr 程序的相关运行参数，指定 jar包
 * 最后提交给yarn
 */

public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        // 如果是在windows平台上跑这个程序，则需要配以下conf，且对平台对环境做相应修改
       // conf.set("mapreduce.framework.name", "yarn");
       // conf.set("yarn.resoucemanager.hostname", "www.mickey06.com");
        Job job = Job.getInstance(conf);

        //指定本程序的jar包所在的本地路径
        /* job.setJar("/home/app/wc.jar"); */
        job.setJarByClass(WordCountDriver.class);

        // 指定本业务job要使用到mapper/reducer业务类
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //指定mapper输出数据到KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //指定最终输出到数据的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

       /* job.setCombinerClass(WordCountCombiner.class);*/
        // 指定使用combiner，以及combiner是哪个类
        job.setCombinerClass(WordCountReducer.class);

        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
        CombineTextInputFormat.setMinInputSplitSize(job, 2091152);

        //指定job输入的原始文件所在目录
        //FileInputFormat.setInputPaths(job, new Path(args[0]));
        // args[0] 中命令行中是jar包的名字
        FileInputFormat.setInputPaths(job, new Path(args[1]));

        //指定job的输出结果所在目录
        //FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
        // job.submit();

        boolean res = job.waitForCompletion(true);

        System.exit(res?0:1);

    }
}
