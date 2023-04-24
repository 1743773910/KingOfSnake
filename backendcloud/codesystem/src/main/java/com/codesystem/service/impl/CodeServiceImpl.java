package com.codesystem.service.impl;

import com.codesystem.service.CodeService;
import com.codesystem.service.impl.utils.CodePool;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodePool codePool;

    @Override
    public String addCode(String phone) {
        if(codePool.addCode(phone)) return "success";
        return "fail";
    }

    @Override
    public String matchingCode(String phone, String code) {
        if(codePool.matchingCode(phone,code)) return "success";
        return "fail";
    }
}
