package kob.backend.controller.user.subuser;

import kob.backend.service.user.subuser.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class AddController {

    @Autowired
    private AddService addService;

    @PostMapping("/api/user/subuser/add/")
    public Map<String , String> add(@RequestParam Map<String, String> data){
        return addService.add(data);
    }
}
