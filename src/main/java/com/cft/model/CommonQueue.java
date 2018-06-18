package com.cft.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Домашний ПК on 11.06.2018.
 */
public class CommonQueue {

    private ConcurrentMap<Long, InternalGroupQueue>  internalQueues = new ConcurrentHashMap<>();

    public volatile int itemsProcessed = 0;

    private  boolean isGroupExists(Long groupId) {
        return internalQueues.containsKey(groupId);
    }

    private  void addItemToGroup(Item item, Long groupId) {
        if (!isGroupExists(groupId)) {
            createInternalQueue(groupId);
        }
        internalQueues.get(groupId).add(item);
    }

    private void createInternalQueue(Long groupId) {
        internalQueues.put(groupId, new InternalGroupQueue(groupId, false));
    }

    public synchronized void put(Item item){
        Long groupId = item.getGroupId();

        if (!isGroupExists(groupId)) {
            createInternalQueue(groupId);
        }
        addItemToGroup(item, groupId);
        notifyAll();
    }

    public InternalGroupQueue getInternalQueueByGroupId(Long groupId){
        return internalQueues.get(groupId);
    }
    
    public synchronized Long getAvailableGroupId(){
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Long> availableGroupIds = new ArrayList<>();
        for(Long key: internalQueues.keySet()){
            if(!internalQueues.get(key).isProcessing()){
                availableGroupIds.add(key);
            }
        }
        int r = new Random().nextInt(availableGroupIds.size());
        Long groupId = availableGroupIds.get(r);
        internalQueues.get(groupId).setProcessing(true);
        return groupId;
    }
    
    public  boolean hasAvailableGroups(){
        for(Long key: internalQueues.keySet()){
            if(!internalQueues.get(key).isProcessing()){
                return true;
            }
        }
        return false;
    }

    public synchronized Item removeElementFromInternalQueue(Long groupId){
        return getInternalQueues().get(groupId).poll();
    }

    public ConcurrentMap<Long, InternalGroupQueue> getInternalQueues() {
        return internalQueues;
    }

    public int getItemsProcessed() {
        return itemsProcessed;
    }

    public void setItemsProcessed(int itemsProcessed) {
        this.itemsProcessed = itemsProcessed;
    }

}
