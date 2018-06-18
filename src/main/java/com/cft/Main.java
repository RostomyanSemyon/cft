package com.cft;

import com.cft.consumer.ConsumerImpl;
import com.cft.model.CommonQueue;
import com.cft.producer.Producer;
import com.cft.producer.ProducerImpl;

/**
 * Created by Домашний ПК on 12.06.2018.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        CommonQueue commonQueue = new CommonQueue();
        Producer producer = new ProducerImpl(args[0], Integer.parseInt(args[1]), commonQueue);
        for (int i = 0; i < Integer.parseInt(args[1]); i++){
            new ConsumerImpl(commonQueue, "Thread #" + i, Integer.parseInt(args[0]));
        }
    }
}
