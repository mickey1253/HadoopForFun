package org.mickey2016.hadoop.mr.wcdemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;


/**
 * Created by Mickey on 10/24/2016.
 * /

/**
 * KEYIN, VALUEIN 对应 mapper 输出到KEYOUT, VALUEOUT 类型对应
 * KEYOUT , VALUEOUT 是自定义reduce逻辑处理结果的输出数据类型
 * VALUEOUT是总次数
 *
 */

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * <Neal, 1>, <Neal, 1>, <Neal, 1>
     * <Hello, 1>, <Hello, 1>, <Hello, 1>
     * 入参key，是一组相同单词 KV 对的 key
     */

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int count = 0;

        /*
        Iterator<IntWritable> iterator = values.iterator();
        while(iterator.hasNext()){
            count += iterator.next().get();
        }
        */

        for(IntWritable value : values){

            count += value.get();

        }

        context.write(key, new IntWritable(count));

    }
}
