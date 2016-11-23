package com.mickey.hadoop.mr.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * Created by Mickey on 7/20/2016.
 */
// public class WCMapper extends Mapper<Long, String, String, Long> {

public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

            String line = value.toString();

            String[] words = StringUtils.split(line, ' ');

        for (String word: words) {
            context.write(new Text(word), new LongWritable(1));

        }
    }
}
