package kob.backend.service.impl.subuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kob.backend.mapper.SubuserMapper;
import kob.backend.pojo.Subuser;
import kob.backend.pojo.User;
import kob.backend.service.impl.utils.UserDetailImpl;
import kob.backend.service.user.subuser.QueryForListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class queryForListServiceImpl implements QueryForListService {
    @Autowired
    private SubuserMapper subuserMapper;

    @Override
    public List<Subuser> queryForList() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Subuser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        return subuserMapper.selectList(queryWrapper);
    }
}
