package kob.backend.controller.shopping;

import com.alibaba.fastjson.JSONObject;
import kob.backend.service.shopping.BotShopCartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@CrossOrigin
public class BotShopCartQueryController {
    @Autowired
    private BotShopCartQueryService botShopCartQueryService;

    @GetMapping("/api/shopping/getcart/")
    public JSONObject CaryQuery(String[] ids, @RequestParam Map<String, String> data){
        Integer page = Integer.valueOf(data.get("page"));
        Integer number = Integer.valueOf(data.get("number"));
        return botShopCartQueryService.query(ids, page, number);
    }
}
