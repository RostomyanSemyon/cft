package com.cft.model;

/**
 * Created by Домашний ПК on 11.06.2018.
 */
public class Item implements Comparable{

    private Long itemId;
    private Long groupId;

    public Item(Long itemId, Long groupId) {
        this.itemId = itemId;
        this.groupId = groupId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !o.getClass().equals(Item.class)){
            return 1;
        } else {
            o.getClass().equals(Item.class);
            Item item = (Item) o;
            return this.getItemId().compareTo(item.getItemId());
        }
    }

    public String toString(){
        return new StringBuilder("Item (").append(getItemId()).append(", ")
                .append(getGroupId()).append(")").toString();
    }
}
