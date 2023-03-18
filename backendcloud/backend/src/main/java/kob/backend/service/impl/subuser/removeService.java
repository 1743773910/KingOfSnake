package kob.backend.service.impl.subuser;

import kob.backend.mapper.SubuserMapper;
import kob.backend.pojo.Subuser;
import kob.backend.pojo.User;
import kob.backend.service.impl.utils.UserDetailImpl;
import kob.backend.service.user.subuser.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class removeService implements RemoveService {

    @Autowired
    private SubuserMapper subuserMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Integer subuser_id = Integer.parseInt(data.get("subuser_id"));
        Subuser subuser = subuserMapper.selectById(subuser_id);
        Map<String , String > map = new HashMap<>();
        if(subuser == null){
            map.put("meassage","subuser不存在");
            return map;
        }
        if(!user.getId().equals(subuser.getUserId())){
            map.put("message", "该subuser不是您的subuser,没有权限删除该subuser");
            return map;
        }
        subuserMapper.deleteById(subuser_id);
        map.put("message", "success");
        return map;
    }
}
