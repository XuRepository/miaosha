package com.hust.miaosha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @program: miaosha1
 * @description: 参数化注解
 * @author: XuJY
 * @create: 2022-02-28 11:02
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserArgumentResolver userArgumentResolver;


    /**
     * {@inheritDoc}
     * 参数化注解，来解决goodsController的参数复杂问题
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
