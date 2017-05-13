package com.netcracker.crm.scheduler.cacher;

/**
 * Created by Pasha on 13.05.2017.
 */
public abstract class Cache<T> {
    public abstract void fillCache();
    public abstract Object getElement(Long key);
    public abstract void putElement(Long key, T element);
    public abstract void removeElement(Long key);
    public abstract void removeElement(Long key, T element);
}
