package com.dscjss.lineup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerTokenServices tokenServices;

    private final JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    public ResourceServerConfig(ResourceServerTokenServices tokenServices, JwtAccessTokenConverter accessTokenConverter) {
        this.tokenServices = tokenServices;
        this.accessTokenConverter = accessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/event_details").permitAll()
                .antMatchers("/api/**" ).authenticated()
                .antMatchers("/swagger-ui.html").permitAll();
    }

}