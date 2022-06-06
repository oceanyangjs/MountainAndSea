package com.mountain.sea.service;

import com.mountain.sea.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 15:44
 */
@Service
//todo 自定义认证方式需要生效
public class AuthenticationUserService implements UserDetailsService {
//public class AuthenticationUserService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(username);
        userDetail.setPassword("123456");
        return userDetail;
    }
}
