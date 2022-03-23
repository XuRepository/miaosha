package com.hust.miaosha.exception;

import com.hust.miaosha.result.CodeMsg;
import com.hust.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: miaosha1
 * @description:全局异常处理
 * @author: XuJY
 * @create: 2022-02-27 20:07
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)//处理全局异常
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){

        e.printStackTrace();

        //全局异常处理，如果e是GlobalException，直接拦截返回
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        } if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();

            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }


    }


}
