package kob.backend.controller.user.account;

import kob.backend.service.user.account.LoginByPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class LoginByPhoneController {
    @Autowired
    private LoginByPhoneService loginByPhoneService;

    @PostMapping("/api/user/account/tokenByPhone/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map){
        String phone = map.get("phone");
        String code = map.get("code");
        String method = map.get("method");
        return loginByPhoneService.getTokenByPhone(phone, code, method);
    }

}
