package cn.zz.framework.jdbc.entity;

import cn.zz.framework.core.entity.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @Author zenghzong
 * @Since 2019/8/28
 * @Version 1.0
 */

@Data
public class Pager extends BaseModel {

	//总行数
	private Integer totalRows;

	//每页数量
	private Integer pageSize = 10;

	// 当前页
	private Integer currentPage;

	// 总页数
	private Integer totalPage = 1;

	//开始行
	private Integer startRow;

	//表单数
	private Integer formNumber;


	private Integer viewBegin = 1;
	private Integer viewEnd = 1;

	// 记录
	private List<?> data;

}