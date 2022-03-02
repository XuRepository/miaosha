package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-02-27 20:37
 **/
public class GlobalException extends RuntimeException{

    private CodeMsg cm;

    public CodeMsg getCm() {
        return cm;
    }

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }
}
