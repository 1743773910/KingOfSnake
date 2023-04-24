package kob.backend.controller.user.account;

import kob.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin()
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/account/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map){
        String botName = map.get("botName");
        String botPwd = map.get("botPwd");
        String phone = map.get("phone");
        return loginService.getToken(botName, botPwd, phone);
    }

}
