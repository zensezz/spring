package cn.zz.framework.core.enums;


/**
 * @Author zenghzong
 * @Since 2019/9/12
 * @Version 1.0
 */
public enum LoggerConstant {
    LOGGER_WRAPPER("LOGGER_WRAPPER");
    String value;

    LoggerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
