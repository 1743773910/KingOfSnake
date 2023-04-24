package kob.backend.controller.leavemessage;

import kob.backend.service.leavemessage.RoastPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin()
public class RoastPostController {
    @Autowired
    private RoastPostService roastPostService;

    @GetMapping("/api/leavemessage/postroast/")
    public Map<String, String> postRoast(@RequestParam Map<String, String> data){
        String name = data.get("name");
        String content = data.get("content");
        return roastPostService.post(name, content);
    }
}
