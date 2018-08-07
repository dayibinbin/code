/**  
 * @Project: sz-xproduct
 * @Title: QueryResult.java
 * @Package cn.com.checknull.model.vo
 * @Description: TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-2-26 上午10:05:11
 * @Copyright: 2015 Pukka Co., Ltd All Rights Reserved.
 * @version v1.0  
 */

package cn.com.checknull.model.vo;

import java.util.List;

/**
 * @ClassName QueryResultGrid
 * @Description 定义一个存放查询结果的泛型类,jqgrid用
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-2-26 上午10:05:11
 */
public class QueryResult<T> {
	// 当前页码
	private long page;
	// 存放页码总数的数据
	private long total;
	// 存放结果集的记录总数
	private long records;
	// 存放结果集
	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

}
