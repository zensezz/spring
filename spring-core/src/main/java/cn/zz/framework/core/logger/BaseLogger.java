package cn.zz.framework.core.logger;

import cn.zz.framework.core.util.LoggerUtil;
import cn.zz.framework.core.util.StringUtil;
import org.apache.log4j.Logger;

/**
 *  rizhi
 * @Author zenghzong
 * @Since 2019/8/27
 * @Version 1.0
 */
public class BaseLogger {

    private Logger logger;

    public BaseLogger(Logger logger) {
        this.logger = logger;
    }


    public static BaseLogger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz);
        return new BaseLogger(logger);
    }
    public static BaseLogger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        return new BaseLogger(logger);
    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(Object message) {
        if (message instanceof String) {
            logger.info(getCurrModule() + message);
            return;
        }
        logger.info(getCurrModule() + message.toString());
    }

    public void debug(Object message) {
        try {
            if (message instanceof String) {
                logger.debug(getCurrModule() + message);
                return;
            }
            logger.debug(getCurrModule() + message.toString());
        } catch (Exception e) {
        }
    }

    public void error(Object message) {
        try {
            if (message instanceof String) {
                logger.error(getCurrModule() + message);
                return;
            }
            logger.error(getCurrModule() + message.toString());
        } catch (Exception e) {
        }
    }

    public void error(Object message, Throwable t) {
        try {
            if (message instanceof String) {
                logger.error(getCurrModule() + message, t);
                return;
            }
            logger.error(getCurrModule() + message.toString(), t);
        } catch (Exception e) {
        }
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
    private String getCurrModule() {
        String module = LoggerUtil.getCurrLog();
        if (StringUtil.isNullOrEmpty(module)) {
            return "";
        }
        return "[" + module + "]";
    }
}
