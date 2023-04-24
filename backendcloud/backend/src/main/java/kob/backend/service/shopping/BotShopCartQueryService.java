package kob.backend.service.shopping;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface BotShopCartQueryService {
    JSONObject query(String[] ids, Integer page, Integer number);
}
