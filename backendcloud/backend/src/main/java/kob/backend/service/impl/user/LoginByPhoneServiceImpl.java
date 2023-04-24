package kob.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kob.backend.mapper.PhoneMapper;
import kob.backend.mapper.UserMapper;
import kob.backend.pojo.Phone;
import kob.backend.pojo.User;
import kob.backend.service.user.account.LoginByPhoneService;
import kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginByPhoneServiceImpl implements LoginByPhoneService {
    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private UserMapper userMapper;

    private static RestTemplate restTemplate;
    private static final String getCodeUrl = "http://localhost:3003/user/account/getCode/";
    private static final String matchingCodeUrl = "http://localhost:3003/user/account/matchingCode/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        LoginByPhoneServiceImpl.restTemplate = restTemplate;
    }
    @Override
    public Map<String, String> getTokenByPhone(String phone, String code, String method) {
        Map<String, String> map = new HashMap<>();
        if(method.equals("getCode")){
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("phone", phone);
            String s = restTemplate.postForObject(getCodeUrl, data, String.class);
            if("success".equals(s)){
                map.put("message", "success");
                return map;
            }else{
                map.put("message", "验证码获取失败,请稍后再试");
                return map;
            }
        } else if (method.equals("matchingCode")) {
            MultiValueMap<String, String> data= new LinkedMultiValueMap<>();
            data.add("phone", phone);
            data.add("code", code);
            String s = restTemplate.postForObject(matchingCodeUrl, data, String.class);
            if("success".equals(s)){
                QueryWrapper<Phone> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("phone", phone);
                Phone phone1 = phoneMapper.selectOne(queryWrapper);
                if(phone1 != null){
                    String username = phone1.getUsername();
                    QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("bot_name", username);
                    User user = userMapper.selectOne(queryWrapper1);
                    String jwt = JwtUtil.createJWT(user.getId().toString());
                    map.put("message", "success");
                    map.put("token", jwt);
                    return map;
                }
                // 如果无，返回前端让用户选择绑定用户还是创建用户
                map.put("message","未绑定用户");
            }else{
                map.put("message","验证码错误");
                return map;
            }
        }
        return map;
    }
}
