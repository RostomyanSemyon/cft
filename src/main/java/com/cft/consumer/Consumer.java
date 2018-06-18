package com.cft.consumer;

import com.cft.model.InternalGroupQueue;

/**
 * Created by Домашний ПК on 12.06.2018.
 */
public interface Consumer<Item> {

    boolean hasItems();

    void changeGroupForProcess();

    void setInternalGroupQueue(InternalGroupQueue internalGroupQueue);

    Long requestAvailableGroup();

    void setGroupId(Long groupId);
}
