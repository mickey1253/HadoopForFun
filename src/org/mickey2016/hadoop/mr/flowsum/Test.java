package org.mickey2016.hadoop.mr.flowsum;

import sun.plugin2.os.windows.FLASHWINFO;

import java.util.ArrayList;

/**
 * Created by Mickey on 2016/11/8.
 */
public class Test {

    public static void main(String[] args){
        ArrayList<FlowBean> arrayList = new ArrayList<FlowBean>();
        FlowBean bean = new FlowBean();

        bean.set(1, 2);
        arrayList.add(bean);

        bean.set(10, 20);
        arrayList.add(bean);

        bean.set(100,200);
        arrayList.add(bean);

        System.out.println(arrayList.size());

        for(FlowBean b: arrayList ){
            System.out.println(b);
        }
    }
}
