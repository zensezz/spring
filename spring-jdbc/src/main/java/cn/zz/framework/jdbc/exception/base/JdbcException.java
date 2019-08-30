package cn.zz.framework.jdbc.exception.base;

import cn.zz.framework.core.exception.base.BaseException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class JdbcException extends BaseException {
    public JdbcException(){
        super();
    }

    public JdbcException(String msg){
        super(msg);
    }

    public JdbcException(String msg,Exception e){
        super(msg, e);
    }
}
 