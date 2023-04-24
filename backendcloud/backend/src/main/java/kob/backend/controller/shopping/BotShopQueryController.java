package kob.backend.controller.shopping;

import com.alibaba.fastjson.JSONObject;
import kob.backend.service.shopping.BotShopQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin()
public class BotShopQueryController {
    @Autowired
    private BotShopQueryService botShopQueryService;

    @GetMapping("/api/shopping/botshop/")
    public JSONObject getList(@RequestParam Map<String, String> data){
        Integer page = Integer.valueOf(data.get("page"));
        return botShopQueryService.getList(page);
    }
}
