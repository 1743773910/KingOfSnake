package kob.backend.controller.user.subuser;

import kob.backend.pojo.Subuser;
import kob.backend.service.user.subuser.QueryForListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin()
public class QueryForListController {
    @Autowired
    private QueryForListService queryForListService;

    @GetMapping("/api/user/subuser/querylist/")
    public List<Subuser> queryList(){
        return queryForListService.queryForList();
    }
}
