package kob.backend.service.user.account;

import java.util.Map;

public interface LoginByPhoneService {
    Map<String, String> getTokenByPhone(String phone, String code, String method);
}
