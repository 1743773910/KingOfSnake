package com.codesystem.controller;

import com.codesystem.service.CodeService;
import com.codesystem.service.impl.utils.CodePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class CodeController {
    @Autowired
    private CodeService codeService;

    @PostMapping("/user/account/getCode/")
    public String codeAdd(@RequestParam MultiValueMap<String, String> data){
        String phone = data.getFirst("phone");
        return codeService.addCode(phone);
    }

    @PostMapping("/user/account/matchingCode/")
    public String matchingCode(@RequestParam MultiValueMap<String, String> data){
        String phone = data.getFirst("phone");
        String code = data.getFirst("code");
        return codeService.matchingCode(phone,code);
    }
}
