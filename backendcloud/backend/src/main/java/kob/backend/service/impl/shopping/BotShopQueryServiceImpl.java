package kob.backend.service.impl.shopping;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kob.backend.mapper.BotShopMapper;
import kob.backend.pojo.BotShop;
import kob.backend.service.shopping.BotShopQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotShopQueryServiceImpl implements BotShopQueryService {
    @Autowired
    private BotShopMapper botShopMapper;

    @Override
    public JSONObject getList(Integer page) {
        IPage<BotShop> botShopIPage = new Page<>(page, 6);
        QueryWrapper<BotShop> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<BotShop> botShops= botShopMapper.selectPage(botShopIPage,queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        List<JSONObject> items = new ArrayList<>();
        for(BotShop botShop : botShops){
            JSONObject item = new JSONObject();
            item.put("id", botShop.getId());
            item.put("name",botShop.getName());
            item.put("price",botShop.getPrice());
            item.put("stock",botShop.getStock());
            item.put("photo",botShop.getPhoto());
            items.add(item);
        }
        resp.put("botsShop",items);
        resp.put("botsShop_cnt", botShopMapper.selectCount(null));
        return resp;
    }
}
