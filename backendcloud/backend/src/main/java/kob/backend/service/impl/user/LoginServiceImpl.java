package kob.backend.service.impl.user;

import kob.backend.service.impl.utils.UserDetailImpl;
import kob.backend.service.user.account.LoginService;
import kob.backend.utils.JwtUtil;
import kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String botName, String botPwd) {
        // 存加密的name和pwd
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(botName, botPwd);
        Authentication authentication = authenticationManager.authenticate(authenticationToken); // 登录失败会自动处理

        // 取出用户
        UserDetailImpl loginUser = (UserDetailImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String jwt = JwtUtil.createJWT(user.getId().toString());
        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("token", jwt);

        return map;
    }



}
