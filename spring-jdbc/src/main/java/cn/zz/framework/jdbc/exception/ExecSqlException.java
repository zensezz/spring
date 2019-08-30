package cn.zz.framework.jdbc.exception;

import cn.zz.framework.jdbc.exception.base.JdbcException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class ExecSqlException extends JdbcException {
    public ExecSqlException() {
        super();
    }

    public ExecSqlException(String msg) {
        super(msg);
    }

    public ExecSqlException(String msg, Exception e) {
        super(msg, e);
    }
}
 