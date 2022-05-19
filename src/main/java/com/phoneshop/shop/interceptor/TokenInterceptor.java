package com.phoneshop.shop.interceptor;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.phoneshop.shop.entity.result.ResultData;
import com.phoneshop.shop.entity.enums.ReturnCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            if (!token.isBlank() && JWTUtil.verify(token, "peytonlee".getBytes())) {
                return true;
            }
        }catch (Exception ignored) {

        }
        ResultData<?> resultData = ResultData.fail(ReturnCode.USER_NO_LOGIN.code, ReturnCode.USER_NO_LOGIN.message);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtil.toJsonStr(resultData));
        return false;
    }
}
