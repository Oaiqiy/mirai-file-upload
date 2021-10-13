package org.oaiqiy.miraifileupload.web;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    /**
     * 安全配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").access("permitAll()")
                .antMatchers("/","/**").access("hasRole('ROLE_ADMIN')")
                                                            //禁用登录可将此处改为"permitAll()"

                .and().formLogin().loginProcessingUrl("/login")
                .and().csrf().disable();
    }
}
