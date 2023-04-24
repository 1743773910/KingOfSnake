package com.codesystem.botrunningsystem.Service.impl.utlis;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private static final ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Queue<Bot> bots = new LinkedList<>();


    public void addBot(Integer userId, String botCode, String input){
        lock.lock();
        try{
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        long timeOut = 2000;
        consumer.startTimeout(timeOut, bot);
    }

    @Override
    public void run(){
        while (true){
            lock.lock();
            try {
                if(bots.isEmpty()){
                    condition.await(); // 阻塞线程,且会自动将锁释放
                }else{
                    Bot bot = bots.remove(); // 返回队头
                    lock.unlock();
                    consume(bot); // 比较耗时,可能会执行几秒钟
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                lock.unlock();
                break;
            }
        }
    }
}
