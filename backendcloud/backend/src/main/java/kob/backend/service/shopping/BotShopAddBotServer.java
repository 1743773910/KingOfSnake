package kob.backend.service.shopping;

import java.util.Map;

public interface BotShopAddBotServer {
    Map<String, String> addBot(Integer subbotId, Integer price);
}
