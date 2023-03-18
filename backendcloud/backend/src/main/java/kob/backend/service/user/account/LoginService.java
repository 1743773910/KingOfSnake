package kob.backend.service.user.account;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String botName, String botPwd);

}
