/**  
* @Project: xproduct-mybatis
* @Title: TLogLoginDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午2:44:18
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.com.checknull.model.po.TLogLogin;

/**
 * @ClassName TLogLoginDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午2:44:18
 */
public class TLogLoginDao extends BaseDao<TLogLogin> {
	
	public TLogLogin find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TLogLogin.findById", id);
	}
	
	public void insert(TLogLogin entity) throws SQLException{
		sqlSessionTemplate.insert("TLogLogin.save", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TLogLogin.deleteById", id);
	}
	
	public List<Map<String, Object>> statisticsPV(TLogLogin queryEntity) throws SQLException{
		
		return sqlSessionTemplate.selectList("TLogLogin.statisticsPV", queryEntity);
	}
	
	public List<Map<String, Object>> statisticsUV(TLogLogin queryEntity) throws SQLException{
		
		return sqlSessionTemplate.selectList("TLogLogin.statisticsUV", queryEntity);
	}
}

