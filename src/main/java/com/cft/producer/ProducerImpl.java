package com.cft.producer;

import com.cft.model.CommonQueue;
import com.cft.model.InternalGroupQueue;
import com.cft.model.Item;

import java.util.Random;

/**
 * Created by Домашний ПК on 11.06.2018.
 */
public class ProducerImpl implements Runnable, Producer {

    private CommonQueue commonQueue;

    private int itemsCount;
    private int groupsCount;

    public ProducerImpl(String itemsCount, int groupsCount, CommonQueue commonQueue){
        this.commonQueue = commonQueue;
        this.itemsCount = Integer.parseInt(itemsCount);
        this.groupsCount = groupsCount;
        new Thread(this).start();
    }


    @Override
    public void run() {
        for(long i =0; i< itemsCount; i++){
            Item item = new Item(i, (long) new Random().nextInt(groupsCount));
            commonQueue.put(item);
            System.out.println(item.toString() + "was added");
        }
    }
}
