package com.cft.consumer;

import com.cft.model.CommonQueue;
import com.cft.model.InternalGroupQueue;

/**
 * Created by Домашний ПК on 11.06.2018.
 */
public class ConsumerImpl implements Runnable, Consumer {

    private static final int OPS_THREASHOLD = 4;

    private CommonQueue commonQueue;

    private InternalGroupQueue internalGroupQueue;
    private Long groupId;
    private int current_ops = 0;
    private int itemsCount;
    private String threadName;

    public ConsumerImpl(CommonQueue commonQueue, String threadName, int itemsCount){
        this.commonQueue = commonQueue;
        this.internalGroupQueue = commonQueue.getInternalQueues().get(requestAvailableGroup());
        this.groupId = internalGroupQueue.getGroupId();
        this.threadName = threadName;
        this.itemsCount = itemsCount;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (commonQueue.getItemsProcessed() < itemsCount){
            if (hasItems()){
                System.out.println(" " + commonQueue.removeElementFromInternalQueue(groupId).toString() + " was processed by " + this.threadName);
                current_ops++;
                commonQueue.setItemsProcessed(commonQueue.getItemsProcessed()+1);
                if((commonQueue.hasAvailableGroups() && checkForChange()) || internalGroupQueue.isEmpty()){
                    commonQueue.getInternalQueues().get(groupId).setProcessing(false);
                    changeGroupForProcess();
                }
            } else if ((commonQueue.hasAvailableGroups() && checkForChange()) || internalGroupQueue.isEmpty()){
                commonQueue.getInternalQueues().get(groupId).setProcessing(false);
                changeGroupForProcess();
            } else {
                current_ops = 0;
            }
        }
    }

    @Override
    public boolean hasItems() {
        return !internalGroupQueue.isEmpty();
    }

    @Override
    public void changeGroupForProcess(){
        Long groupId = requestAvailableGroup();
        setGroupId(groupId);
        setInternalGroupQueue(commonQueue.getInternalQueueByGroupId(groupId));
    }

    private boolean checkForChange(){
        return current_ops > OPS_THREASHOLD;
    }

    @Override
    public void setInternalGroupQueue(InternalGroupQueue internalGroupQueue) {
        this.internalGroupQueue = internalGroupQueue;
    }

    @Override
    public Long requestAvailableGroup(){
        return commonQueue.getAvailableGroupId();
    }

    @Override
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
