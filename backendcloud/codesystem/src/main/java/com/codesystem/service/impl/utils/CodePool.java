package com.codesystem.service.impl.utils;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;



import java.util.concurrent.TimeUnit;


@Component
public class CodePool{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Boolean addCode(String phone){
        String code = productCode();
        redisTemplate.opsForValue().set(phone,code);
        redisTemplate.opsForValue().getOperations().expire(phone, 5 * 60, TimeUnit.SECONDS);
        return true;
    }

    public String productCode(){
        String code = "1234";
        return code;
    }

    public boolean matchingCode(String phone, String code){
        Object ret = redisTemplate.opsForValue().get(phone);
        return ret != null && ret.equals(code);
    }
}
