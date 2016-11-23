package org.mickey2016.hadoop.mr.rjoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.mickey2016.hadoop.mr.wcdemo.WordCountDriver;
import org.mickey2016.hadoop.mr.wcdemo.WordCountReducer;
import org.mickey2016.hadoop.mr.wcdemo.WordcountMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mickey on 2016/11/17.
 */
public class RJoin {

    static class RJoinMapper extends Mapper<LongWritable, Text, Text, InfoBean> {

        @Override
        protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {

            String line = value.toString();
            InfoBean bean = new InfoBean();
            Text k = new Text();
            String pid = "";

            //InputSplit inputSplict = context.getInputSplit();
            // InputSplit是最高的抽象类，具体可以是文本切片，图片切片，视频切片。。。
            FileSplit inputSplict = (FileSplit) context.getInputSplit();
            String name = inputSplict.getPath().getName();

            if (name.startsWith("order")) {

                String[] fields = line.split(",");

                // id  date  pid   amount
                pid = fields[2];

                bean.set(Integer.parseInt(fields[0]),
                        fields[1],
                        pid,
                        Integer.parseInt(fields[3]),
                        "",
                        0,
                        0,
                        "0");
            } else {

                String[] fields = line.split(",");
                pid = fields[0];

                bean.set(0,
                        "",
                        pid,
                        0,
                        fields[1],
                        Integer.parseInt(fields[2]),
                        Float.parseFloat(fields[3]),
                        "1");

            }
            k.set(pid);
            context.write(k, bean);

        }
    }


    static class RJoinReducer extends Reducer<Text, InfoBean, InfoBean, NullWritable> {

        @Override
        protected void reduce(Text pid, Iterable<InfoBean> beans, Context context) throws IOException, InterruptedException {

            InfoBean pdBean = new InfoBean();
            ArrayList<InfoBean> orderBeans = new ArrayList<InfoBean>();

            for(InfoBean bean: beans){

                if("1".equals(bean.getFlag())){

                    try{

                        BeanUtils.copyProperties(pdBean, bean);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else{

                    InfoBean obean = new InfoBean();

                    try{

                        BeanUtils.copyProperties(obean, bean);
                        orderBeans.add(obean);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }


            // 拼接两类数据类型形成最终结果
            for(InfoBean bean: orderBeans){

                bean.setPname(pdBean.getPname());
                bean.setCategory_id(pdBean.getCategory_id());
                bean.setPrice(pdBean.getPrice());

                context.write(bean, NullWritable.get());

            }
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);


        /* job.setJar("/home/app/wc.jar"); */
        job.setJarByClass(RJoin.class);

        // 指定本业务job要使用到mapper/reducer业务类
        job.setMapperClass(RJoinMapper.class);
        job.setReducerClass(RJoinReducer.class);

        //指定mapper输出数据到KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(InfoBean.class);

        //指定最终输出到数据的KV类型
        job.setOutputKeyClass(InfoBean.class);
        job.setOutputValueClass(NullWritable.class);

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