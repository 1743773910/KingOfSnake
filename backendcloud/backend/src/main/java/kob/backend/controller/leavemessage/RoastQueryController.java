package kob.backend.controller.leavemessage;

import com.alibaba.fastjson.JSONObject;
import kob.backend.service.leavemessage.RoastQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin()
public class RoastQueryController {
    @Autowired
    private RoastQueryService roastQueryService;

    @GetMapping("/api/leavemessage/getroast/")
    public JSONObject queryRoast(@RequestParam Map<String, String> data){
        Integer number = Integer.valueOf(data.get("number"));
        return roastQueryService.query(number);
    }
}
