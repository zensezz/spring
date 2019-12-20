package cn.zz.framework.core.test;

import cn.zz.framework.core.annotation.AutoBean;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
public class Main {
    @AutoBean
    static  CoreTest coreTest;

    public static void main(String[] args) {
        coreTest.test();
    }
}
 