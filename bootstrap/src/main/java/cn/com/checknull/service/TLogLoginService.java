/**  
* @Project: xproduct
* @Title: TLogLoginService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 下午4:31:37
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TLogLogin;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TLogLoginService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   下午4:31:37
 */
@Transactional
public class TLogLoginService extends BaseService{

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TLogLogin>  findPage(int pageIndex, int pageSize, TLogLogin queryEntity){
		return tlogLoginDao.findPage(pageIndex, pageSize, queryEntity);
	}

	 @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TLogLogin find(Long id) throws SQLException{
		return tlogLoginDao.find(id);
	}
	
	public void insert(TLogLogin entity) throws SQLException{
		tlogLoginDao.insert(entity);
	}
	
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			tlogLoginDao.delete(id);
		}
	}
	
	public List<Map<String, Object>> statisticsPV(TLogLogin queryEntity) throws SQLException{
		return tlogLoginDao.statisticsPV(queryEntity);
	}
	
	public List<Map<String, Object>> statisticsUV(TLogLogin queryEntity) throws SQLException{
		return tlogLoginDao.statisticsUV(queryEntity);
	}
}

