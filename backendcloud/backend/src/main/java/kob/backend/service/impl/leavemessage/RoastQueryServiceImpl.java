package kob.backend.service.impl.leavemessage;

import com.alibaba.fastjson.JSONObject;

import kob.backend.service.leavemessage.RoastQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;


@Service
public class RoastQueryServiceImpl implements RoastQueryService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JSONObject query(Integer number) {
        JSONObject resp = new JSONObject();
        List<JSONObject> items = new LinkedList<>();
        int length = Math.toIntExact(redisTemplate.opsForList().size("roast"));
        for(int i = Math.max(0, length - number * 100); i < length; i++){
            JSONObject item = new JSONObject();
            RoastPostServiceImpl.Roast roast = (RoastPostServiceImpl.Roast) redisTemplate.opsForList().index("roast", i);
            item.put("name", roast.getName());
            item.put("content", roast.getContent());
            item.put("time", roast.getPostTime());
            items.add(item);
        }
        resp.put("roastitems", items);
        return resp;
    }
}
