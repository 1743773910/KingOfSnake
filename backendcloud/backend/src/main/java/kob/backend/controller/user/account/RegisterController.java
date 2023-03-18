package kob.backend.controller.user.account;

import kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/api/user/account/register/")
    public Map<String, String> register(@RequestParam Map<String, String> map){
        String botName = map.get("botName");
        String botPwd = map.get("botPwd");
        String confirmedPwd = map.get("confirmedPwd");
        return registerService.register(botName, botPwd, confirmedPwd);
    }
}
