package cn.zz.framework.jdbc.annotation;

import cn.zz.framework.jdbc.JdbcHandle;


import java.util.List;
import java.util.Map;


/**
 * @Author zenghzong
 * @Since 2019/9/12
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        JdbcHandle handle = new JdbcHandle();
        // 需要加载dataSource
        String sql = "select * from bs2_task";
        List<Map<String, Object>> maps = handle.baseQuery(sql);
        maps.forEach(System.out::println);
    }
}
