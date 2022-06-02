package com.mountain.sea.config;

import com.mountain.sea.security.*;
import com.mountain.sea.service.AuthenticationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-01 19:40
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationUserService authenticationUserService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("admin").password("123456").roles("ADMIN");
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

//    private AuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(authenticationUserService);
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setHideUserNotFoundExceptions(false);
//        return provider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        登陆处理
        http.formLogin().loginProcessingUrl("/api/v1/auth/login").successHandler(loginSuccessHandler()).failureHandler(loginFailureHandler()).
//                登出处理
                and().logout().addLogoutHandler(logoutOptionHandler()).logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout")).logoutSuccessHandler(logoutSuccessResponseHandler())
//                通用访问异常处理
                .and().csrf().disable().exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/actuator/**").permitAll().anyRequest().authenticated().and();
        http.headers().cacheControl();
    }

    private AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    private LogoutSuccessHandler logoutSuccessResponseHandler() {
        return new LogoutSuccessHandler();
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    private LogoutHandler logoutOptionHandler() {
        return new LogoutOptionHandler();
    }

    private AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
