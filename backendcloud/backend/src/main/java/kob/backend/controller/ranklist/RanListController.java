package kob.backend.controller.ranklist;

import com.alibaba.fastjson.JSONObject;
import kob.backend.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class RanListController {
    @Autowired
    private RankListService rankListService;

    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return rankListService.getList(page);
    }

}
