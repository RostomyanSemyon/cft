package com.cft.model;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Домашний ПК on 12.06.2018.
 */
public class InternalGroupQueue extends PriorityBlockingQueue<Item> {

    private Long groupId;
    private boolean processing = false;

    public InternalGroupQueue(Long groupId, boolean processing){
        this.groupId = groupId;
        this.processing = processing;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public Long getGroupId() {
        return groupId;
    }

}
