package com.hust.miaosha.config;

import com.hust.miaosha.domain.MiaoshaUser;
import com.hust.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: miaosha1
 * @description: 这里是如何实现 MiaoShaUser 的参数化注解的？
 * @author: XuJY
 * @create: 2022-02-28 11:05
 **/
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService miaoshaUserService;


    /**
     * 写明支持的参数化注解的类为miaoshauser,即：
     * 如果参数中出现MiaoshaUser类型的参数，自动执行UserArgumentResolver的resolveArgument()
     * 这样的好处是，当在很多方法中需要对参数进行操作并且得到一个MiaoshaUser的时候，可以通过
     * resolver来执行这一部分操作，而不必每次都重复代码。
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    /**
     * 参数检查
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);

        //如果请求没有带上cookie，则认为当前请求未登录，转到登录页面。
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return miaoshaUserService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[] cookies = request.getCookies();
        //避免空指针
        if (cookies ==null || cookies.length==0){
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
