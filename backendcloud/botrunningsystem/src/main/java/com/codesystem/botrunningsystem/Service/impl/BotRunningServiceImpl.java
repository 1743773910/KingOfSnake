package com.codesystem.botrunningsystem.Service.impl;

import com.codesystem.botrunningsystem.Service.BotRunningService;
import com.codesystem.botrunningsystem.Service.impl.utlis.BotPool;
import org.springframework.stereotype.Service;

@Service
public class BotRunningServiceImpl implements BotRunningService {
    @Override
    public String addBot(Integer userId, String botCode, String input) {
        BotPool botPool = new BotPool();
        botPool.start();
        botPool.addBot(userId, botCode, input);
        return "bot add success";
    }
}
