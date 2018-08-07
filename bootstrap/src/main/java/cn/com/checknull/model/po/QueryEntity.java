/**  
* @Project: xproduct-mybatis
* @Title: QueryEntity.java
* @Package cn.com.checknull.entity
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-6-3 下午1:59:06
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.model.po;
/**
 * @ClassName QueryEntity
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-6-3   下午1:59:06
 */
public abstract class QueryEntity {
	private int firstIndex;
	private int lastIndex;
	private String order;
	private String sort;
	
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}

