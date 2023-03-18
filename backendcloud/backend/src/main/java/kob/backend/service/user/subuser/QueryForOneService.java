package kob.backend.service.user.subuser;

import kob.backend.pojo.Subuser;

import java.util.Map;

public interface QueryForOneService {
    Subuser queryForOne(Map<String, String> data);
}
