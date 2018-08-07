/**  
* @Project: xproduct-mybatis
* @Title: BaseDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午2:43:41
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

import cn.com.checknull.model.po.QueryEntity;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName BaseDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午2:43:41
 */
public class BaseDao<T> {
	
	@Resource(name="sqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public QueryResult<T>  findPage(int pageIndex, int pageSize, QueryEntity queryEntity){
		QueryResult<T> qr = new QueryResult<T>();
		String simpleName = queryEntity.getClass().getSimpleName();
		String countStatement = simpleName + ".findPageCount";
		Long totalCount = (Long) sqlSessionTemplate.selectOne(countStatement, queryEntity);
		if (totalCount == null){
			totalCount = 0L;
		}
		qr.setRecords(totalCount);
		List<T> rows = new ArrayList<T>();
		if (totalCount > 0){
			int firstIndex = 1;
			int lastIndex = 1;
			if (pageIndex != -1 && pageSize != -1){
				if (pageIndex < 1) firstIndex = 1;
				else firstIndex = pageIndex;
				if (pageSize < 1) lastIndex = 1;
				else lastIndex = pageSize;
				firstIndex = (firstIndex-1) * pageSize;
			}else{
				firstIndex = -1;
				lastIndex = -1;
			}
			queryEntity.setFirstIndex(firstIndex);
			queryEntity.setLastIndex(lastIndex);
			String pageStatement = simpleName + ".findPage";
			rows = sqlSessionTemplate.selectList(pageStatement, queryEntity);
		}
		qr.setPage(pageIndex);
		qr.setTotal(totalCount/pageSize+1);
		qr.setRows(rows);
		return qr;
	}
	
}

