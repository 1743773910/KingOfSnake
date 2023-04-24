package kob.backend.service.impl.shopping;

import kob.backend.mapper.BotShopMapper;
import kob.backend.mapper.SubuserMapper;
import kob.backend.pojo.BotShop;
import kob.backend.pojo.Subuser;
import kob.backend.service.shopping.BotShopAddBotServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BotShopAddBotServiceImpl implements BotShopAddBotServer {
    @Autowired
    private BotShopMapper botShopMapper;

    @Autowired
    private SubuserMapper subuserMapper;

    @Override
    public Map<String, String> addBot(Integer subbotId, Integer price) {
        Map<String, String> map = new HashMap<>();
        Subuser subuser = subuserMapper.selectById(subbotId);
        String photo = "E:\\maven\\apache-maven-3.9.0-bin\\apache-maven-3.9.0\\mavenWorkSpace\\kob\\backendcloud\\backend\\src\\main\\resources\\static\\image\\img.png";
        BotShop botShop = new BotShop(null,subuser.getSubbotName(),price,1000,subuser.getDescription(),subuser.getContent(),photo,1,subuser.getCreateTime());
        botShopMapper.insert(botShop);
        map.put("message","success");
        return map;
    }
}
