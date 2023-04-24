package kob.backend.service.shopping;

import com.alibaba.fastjson.JSONObject;

public interface BotShopQueryService {
    JSONObject getList(Integer page);
}
