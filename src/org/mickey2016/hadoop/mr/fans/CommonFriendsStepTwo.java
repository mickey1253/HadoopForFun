package org.mickey2016.hadoop.mr.fans;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.mickey2016.hadoop.mr.inverseindex.InvertIndexStepTwo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mickey on 2016/11/22.
 */
public class CommonFriendsStepTwo {

    static class CommnFriendsStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            // A       I,K,C,B,G,F,H,O,D,
            // 友 人， 人，人
            String line = value.toString();
            String[] person_friends = line.split("\t");

            String friend = person_friends[0];
            String[] persons = person_friends[1].split(",");
            Arrays.sort(persons);

            for(int i=0; i<persons.length-2; i++){
                for(int j=i+1; j<persons.length-1; j++)
                //输出<人-人，好友>，这样相同的 “人-人”对的所有好友就会到同一个reduce中去
                context.write(new Text(persons[i] + "-" + persons[j]), new Text(friend));
            }
        }
    }

    static class CommnFriendsStepTwoReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text person_person, Iterable<Text> friends, Context context) throws IOException, InterruptedException {

            StringBuffer sb = new StringBuffer();

            for (Text friend : friends) {
                sb.append(friend).append(" ");
            }

            context.write(person_person, new Text(sb.toString()));
        }
    }


    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setJarByClass(InvertIndexStepTwo.class);

        job.setMapperClass(CommnFriendsStepTwoMapper.class);
        job.setReducerClass(CommnFriendsStepTwoReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));


        boolean res = job.waitForCompletion(true);

        System.exit(res?0:1);
    }

}
