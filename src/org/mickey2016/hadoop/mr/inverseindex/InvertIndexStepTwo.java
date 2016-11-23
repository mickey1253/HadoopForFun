package org.mickey2016.hadoop.mr.inverseindex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Mickey on 2016/11/21.
 */
public class InvertIndexStepTwo {

    static class InverIndexStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();

            // 第二步需要以"--"来切分
            String[] word_file = line.split("--");
            // hello	c.txt	2b.txt	2a.txt	3
            context.write(new Text(word_file[0]),new Text(word_file[1].replace("\t", "-->") + " "));
        }
    }

    static class InverIndexStepTwoReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            StringBuffer sb = new StringBuffer();

            for (Text value: values) {

                sb.append(value.toString());
            }

            context.write(key,new Text(sb.toString()));

        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(InvertIndexStepTwo.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        job.setMapperClass(InverIndexStepTwoMapper.class);
        job.setReducerClass(InverIndexStepTwoReducer.class);

        boolean res = job.waitForCompletion(true);

        System.exit(res?0:1);
    }

}
