package com.sdp.servflow.logSer.flowControl.model;

public class PerServerRateLimitPolicy extends RateLimitPolicy {
    public PerServerRateLimitPolicy(long capacity, long intervalInMills, long maxBurstTime) {
        super(capacity, intervalInMills, maxBurstTime);
    }

    @Override
    public String genBucketKey(String identity) {
        return "rate:limiter:" + getIntervalInMills() + ":" + getCapacity() + ":" + identity;
    }
    
}