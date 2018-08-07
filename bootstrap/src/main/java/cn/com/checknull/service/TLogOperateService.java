/**  
* @Project: xproduct
* @Title: TLogOperateService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 下午4:32:15
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TLogOperate;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TLogOperateService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   下午4:32:15
 */
@Transactional
public class TLogOperateService extends BaseService{
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TLogOperate>  findPage(int pageIndex, int pageSize, TLogOperate queryEntity){
		return tlogOperateDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	 @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TLogOperate find(Long id) throws SQLException{
		return tlogOperateDao.find(id);
	}
	
	public void insert(TLogOperate entity) throws SQLException{
		tlogOperateDao.insert(entity);
	}
	
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			tlogOperateDao.delete(id);
		}
	}
}

