package kob.backend.controller.user.subuser;

import kob.backend.service.user.subuser.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class RemoveController {

    @Autowired
    private RemoveService removeService;

    @PostMapping("/api/user/subuser/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data){
        return removeService.remove(data);
    }
}
