package kob.backend.service.impl.user;

import kob.backend.service.impl.utils.UserDetailImpl;
import kob.backend.service.user.account.InfoService;
import kob.backend.pojo.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public Map<String, String> getInfo() {
        // 从页面的上下文中得到User
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("id", user.getId().toString());
        map.put("botName", user.getBotName());
        map.put("photo", user.getPhoto());
        return map;
    }
}
