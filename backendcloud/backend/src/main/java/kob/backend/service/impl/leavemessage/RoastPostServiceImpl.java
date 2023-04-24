package kob.backend.service.impl.leavemessage;


import com.fasterxml.jackson.annotation.JsonFormat;
import kob.backend.service.leavemessage.RoastPostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RoastPostServiceImpl implements RoastPostService {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Roast{
        private String name;
        private String content;
        private String postTime;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, String> post(String name, String content) {
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm:ss");
        String nowStr=sdf.format(new Date());
        Roast roast = new Roast(name,content,nowStr);
        redisTemplate.opsForList().rightPush("roast", roast);
        redisTemplate.expire("roast",1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("message","success");
        return map;
    }
}
