package kob.backend.service.impl.user;

import kob.backend.service.user.account.RegisterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kob.backend.mapper.UserMapper;
import kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String botName, String botPwd, String confirmPwd) {
        Map<String, String> map = new HashMap<>();
        if(botName == null || botName.trim().length() == 0){
            map.put("message", "用户名不能为空");
            return map;
        }
        if(botPwd == null || confirmPwd == null || botPwd.length() == 0 || confirmPwd.length() == 0){
            map.put("message", "密码或确认密码不能为空");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_name", botName);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            map.put("message","用户名已存在");
            return map;
        }

        botName = botName.trim();
        if(botName.length() > 20){
            map.put("message","用户名长度不能大于20");
            return map;
        }
        if(botPwd.length() > 100 || confirmPwd.length() > 100){
            map.put("message","密码长度不能大于100");
            return map;
        }
        if(!botPwd.equals(confirmPwd)){
            map.put("message", "两次输入密码不一致");
            return map;
        }

        String encodedPwd = passwordEncoder.encode(botPwd);
        String photo = "E:\\maven\\apache-maven-3.9.0-bin\\apache-maven-3.9.0\\mavenWorkSpace\\KingOfBot\\kob\\backend\\src\\main\\resources\\static\\image\\paimeng.jpg";
        User user = new User(null, botName, encodedPwd, 1500, photo);
        userMapper.insert(user);
        map.put("message", "success");
        return map;
    }
}
