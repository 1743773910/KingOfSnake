package kob.backend.controller.shopping;

import kob.backend.service.shopping.BotShopAddBotServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin()
public class BotShopAddBotController {
    @Autowired
    private BotShopAddBotServer botShopAddBotServer;

    @GetMapping("/api/shopping/botshopadd/")
    public Map<String, String> botShopAdd(@RequestParam Map<String, String> data){
        Integer subbotId = Integer.valueOf(data.get("id"));
        Integer price = Integer.valueOf(data.get("price"));
        return botShopAddBotServer.addBot(subbotId, price);
    }

}
