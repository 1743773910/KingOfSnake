package kob.backend.service.impl.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kob.backend.mapper.UserMapper;
import kob.backend.pojo.User;
import kob.backend.service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankListServiceImpl implements RankListService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {
        IPage<User> userIPage = new Page<>(page, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("bot_rating");
        List<User> users = userMapper.selectPage(userIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        for(User user : users){
            user.setBotPwd("");
        }
        resp.put("users", users);
        resp.put("users_cnt", userMapper.selectCount(null));
        return resp;
    }
}
