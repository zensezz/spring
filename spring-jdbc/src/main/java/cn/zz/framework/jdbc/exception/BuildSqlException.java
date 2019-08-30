package cn.zz.framework.jdbc.exception;

import cn.zz.framework.jdbc.exception.base.JdbcException;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class BuildSqlException extends JdbcException {

    public BuildSqlException() {
        super();
    }

    public BuildSqlException(String msg) {
        super(msg);
    }

    public BuildSqlException(String msg, Exception e) {
        super(msg, e);
    }
}
 