package com.netcracker.crm.datagenerator;

import com.netcracker.crm.service.RandomString;

import java.util.List;
import java.util.Random;

/**
 * Created by Pasha on 05.05.2017.
 */
public abstract class AbstractSetter<T> {

    protected RandomString randomString;
    protected Random random;

    public AbstractSetter() {
        randomString= new RandomString(50);
        random = new Random();
    }

    public abstract List<T> generate(int numbers);
    public abstract T generateObject();
}
