package com.ezreal.autobi.testApi;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
public class LeakyBucketWater {
    long lastModifyTime = 0L;

    long currentWater = 0L;

    long capacity;

    long rate = 2L;


    public LeakyBucketWater(long capacity) {
        this.capacity = capacity;
    }

    public Boolean doProcess() {
        long currentTimeMillis = System.currentTimeMillis();
        // 每分钟出水的个数，如何体现固定限流(currentTimeMillis - lastModifyTime) / 1000  取余的操作
        long outWater = (currentTimeMillis - lastModifyTime) / 1000 * rate;

        // 当前水的容量大小
        currentWater = Math.max(0, currentWater - outWater);

        if (currentWater < capacity) {
            lastModifyTime = currentTimeMillis;
            currentWater++;
            return true;
        } else {
            return false;
        }
    }
}
