package cn.zz.framework.jdbc.exception;

import cn.zz.framework.jdbc.exception.base.JdbcException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class BuildResultException extends JdbcException {
    public BuildResultException() {
        super();
    }

    public BuildResultException(String msg) {
        super(msg);
    }

    public BuildResultException(String msg, Exception e) {
        super(msg, e);
    }
}
 