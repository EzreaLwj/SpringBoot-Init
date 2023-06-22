package com.ezreal.autobi.testApi;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Slf4j
public class LeakyBucket {

    private int rate;

    private ArrayDeque<Object> arrayDeque;

    private int size;

    public LeakyBucket(int rate, int size) {
        this.rate = rate;
        this.size = size;
        arrayDeque = new ArrayDeque<>(size);
    }

    public void addBucket(Object o) {
        if (arrayDeque.size() < size) {
            addBucket(o);
            doProcess();
        } else {
            doReject(o);
        }
    }

    public void doProcess() {
        Object first = arrayDeque.pollFirst();

        if (first == null) {
            log.info("此时漏桶为空");
        } else {

            // 调用业务的方法

            try {
                Thread.sleep(1000 / rate);
                doProcess();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doReject(Object o) {
        log.info("进行限流处理");
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayDeque<Object> getArrayDeque() {
        return arrayDeque;
    }

    public void setArrayDeque(ArrayDeque<Object> arrayDeque) {
        this.arrayDeque = arrayDeque;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
