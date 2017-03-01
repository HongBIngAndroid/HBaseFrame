package com.lf.tempcore.tempConfig;

/**
 * Created by longf on 2016/3/15.
 */
public class CountDownConfig {
    private  final int interval=1000;//时间间隔(毫秒)
    private  long countDown=0;

    public CountDownConfig() {
        init();
    }

    public long getCountDown() {
        return countDown;
    }

    public int getInterval() {
        return interval;
    }

    public void setCountDown(long countDown) {
        this.countDown = countDown;
    }
    public long down(){
        countDown++;
        return countDown;
    }
    public void init(){
        this.countDown=0;

    }
}
