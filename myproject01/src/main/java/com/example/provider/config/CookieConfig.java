package com.example.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

//@Configuration
//public class CookieConfig {
//    //增加一个cookie
//    @Bean
//    public CookieSerializer cookieSerializer(){
//        DefaultCookieSerializer cookie = new DefaultCookieSerializer();
//        cookie.setCookieName("TT_TOKEN");
//        cookie.setUseHttpOnlyCookie(false);
//        return cookie;
//    }
//    //sessionid解析器
//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver(){
//        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
//        resolver.setCookieSerializer(cookieSerializer());
//        return resolver;
//    }
//}
