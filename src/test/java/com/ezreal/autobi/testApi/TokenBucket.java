package com.ezreal.autobi.testApi;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
public class TokenBucket {
    long lastModifyTime = 0L;

    long bucketCounts = 10L;

    long capacity = 50L;

    long currentBucket = 0;


    public Boolean doProcess() {
        long currentTimeMillis = System.currentTimeMillis();
        long generateBucket = (currentTimeMillis - lastModifyTime) / 1000 * bucketCounts;
        currentBucket = Math.min(capacity, generateBucket + currentBucket);
        lastModifyTime = currentTimeMillis;

        if (currentBucket > 0) {
            currentBucket--;
            return true;
        } else {
            return false;
        }
    }
}
