package com.kob.matchingsystem.service.impl.utlis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<MatchingPlayer> matchingPlayers = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private static final String startGameUrl = "http://localhost:3000/api/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating, Integer botId){
        lock.lock();
        try{
            matchingPlayers.add(new MatchingPlayer(userId, rating, 0, botId));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try{
            List<MatchingPlayer> newMatchingPlayers = new ArrayList<>();
            for(MatchingPlayer player : matchingPlayers){
                if(!player.getUserId().equals(userId)){
                    newMatchingPlayers.add(player);
                }
                matchingPlayers = newMatchingPlayers;
            }
        }finally {
            lock.unlock();
        }
    }
    private void increaseWaitingTime() {
        for(MatchingPlayer player : matchingPlayers){
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

    private boolean checkMatch(MatchingPlayer a, MatchingPlayer b){
        // 判断两名玩家是否匹配
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waitingTime * 20;
    }

    private void senResult(MatchingPlayer a, MatchingPlayer b){
        // 返回a和b的匹配结果
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_bot_id", b.getBotId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void matchPlayers(){
        boolean[] used = new boolean[matchingPlayers.size()];
        for(int i = 0; i < matchingPlayers.size(); i++){
            if(used[i]) continue;
            for(int j = i+1; j < matchingPlayers.size(); j++){
                if(used[j]) continue;
                MatchingPlayer a = matchingPlayers.get(i), b = matchingPlayers.get(j);
                if(checkMatch(a,b)){
                    used[i] = used[j] = true;
                    senResult(a,b);
                    break;
                }
            }
        }

        List<MatchingPlayer> newMatchingPlayers = new ArrayList<>();
        for(int i = 0; i < matchingPlayers.size(); i++){
            if(!used[i]) newMatchingPlayers.add(matchingPlayers.get(i));
        }
        matchingPlayers = newMatchingPlayers;
    }
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);
                try{
                    lock.lock();
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
}
