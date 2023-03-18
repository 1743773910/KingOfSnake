package kob.backend.service.ranklist;

import com.alibaba.fastjson.JSONObject;

public interface RankListService {
    JSONObject getList(Integer page);
}
