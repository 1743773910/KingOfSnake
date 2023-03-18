package kob.backend.service.user.account;

import java.util.Map;

public interface RegisterService {
    public Map<String, String> register(String botName, String botPwd, String confirmPwd);
}
