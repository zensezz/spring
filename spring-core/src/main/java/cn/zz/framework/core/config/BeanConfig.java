package cn.zz.framework.core.config;

import cn.zz.framework.core.build.ConfigBuilder;
import cn.zz.framework.core.util.StringUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;

/**
 * @Author zenghzong
 * @Since 2019/8/23
 * @Version 1.0
 */
public class BeanConfig {

    /**
     * 配置前缀
     */
    public static final String PREFIX = "zz";

    /**
     * BeanName
     */
    public static final String BEAN_NAME = "bean";

    /**
     * Property
     */
    public static final String PROPERTY = "property";
    /**
     * ClassName
     */
    public static final String CLASS_NAME="class";
    /**
     * Bean基础配置
     */
    public static final String BEAN_CONFIG_MAPPER = PREFIX + "\\.bean\\.${" + BEAN_NAME + "}\\.${" + PROPERTY + "}";
    /**
     * Bean构造参数配置
     */
    public static final String BEAN_PARAMENT_MAPPER = PREFIX + "\\.bean\\.${" + BEAN_NAME + "}\\.parament\\.${" + PROPERTY + "}";
    /**
     * Bean字段配置
     */
    public static final String BEAN_FIELD_MAPPER = PREFIX + "\\.bean\\.${" + BEAN_NAME + "}\\.field\\.${" + PROPERTY + "}";
    /**
     * Bean表达式
     */
    public static final String INPUT_BEAN_MAPPER = "\\$\\{([A-Za-z0-9_]+)\\}";
    /**
     * 扫描的包配置
     */
    public String packager = "cn.zz.framework";

    /**
     * 要启动的组件
     */
    public String assember = "";

    public void init() throws IllegalArgumentException, IllegalAccessException, IOException, URISyntaxException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            String configField = PREFIX + "." + field.getName();
            String configValue = ConfigBuilder.getProperty(configField);
            if (StringUtil.isNullOrEmpty(configValue)) {
                continue;
            }
            field.setAccessible(true);
            String defaulltValue = (String) field.get(this);
            if (StringUtil.isNullOrEmpty(defaulltValue)) {
                field.set(this, configValue);
                continue;
            }
            configValue = defaulltValue + "," + configValue;
            field.set(this, configValue);
        }

    }
}

 