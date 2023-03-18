package kob.backend.service;

import kob.backend.service.impl.utils.UserDetailImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kob.backend.mapper.UserMapper;
import kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String botName) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bot_name",botName);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw  new RuntimeException("用户不存在");
        }
        return new UserDetailImpl(user);
    }
}
