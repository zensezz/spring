package cn.zz.framework.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  JDBC实体
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JDBCEntity {

	private String sql;
	
	private Object[]params;



}