package kob.backend.service.impl.shopping;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kob.backend.mapper.BotShopMapper;
import kob.backend.pojo.BotShop;
import kob.backend.service.shopping.BotShopCartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
public class BotShopCaryQueryServiceImpl implements BotShopCartQueryService {
    @Autowired
    private BotShopMapper botShopMapper;

    @Override
    public JSONObject query(String[] ids, Integer page, Integer number) {
        JSONObject resp = new JSONObject();
        if(ids == null || ids.length == 0){
            resp.put("message", "购物车暂无商品");
            return resp;
        }
        List<JSONObject> items = new ArrayList<>();
        int length = ids.length;
        for(int i = (page - 1) * number; i < length && i < page * number; i++){
            JSONObject item = new JSONObject();
            BotShop botShop = botShopMapper.selectById(parseInt(ids[i]));
            item.put("id",botShop.getId());
            item.put("name", botShop.getName());
            item.put("photo", botShop.getPhoto());
            item.put("price", botShop.getPrice());
            items.add(item);
        }
        resp.put("shopitems", items);
        resp.put("shopitems_cnt", length);
        return resp;
    }
}
