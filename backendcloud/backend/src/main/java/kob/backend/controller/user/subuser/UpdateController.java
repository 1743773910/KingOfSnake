package kob.backend.controller.user.subuser;

import kob.backend.service.user.subuser.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class UpdateController {

    @Autowired
    private UpdateService updateService;

    @PostMapping("/api/user/subuser/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data){
        System.out.println(data);
        return updateService.update(data);
    }
}
