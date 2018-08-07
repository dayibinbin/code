/**  
* @Project: xproduct-mybatis
* @Title: TOperatorDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午5:43:59
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;

import cn.com.checknull.model.po.TOperator;

/**
 * @ClassName TOperatorDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午5:43:59
 */
public class TOperatorDao extends BaseDao<TOperator>  {
	
	public TOperator find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TOperator.findById", id);
	}
	
	public TOperator findByLoginName(String loginName) throws SQLException{
		return sqlSessionTemplate.selectOne("TOperator.findByLoginName", loginName);
	}
	
	public void insert(TOperator entity) throws SQLException{
		sqlSessionTemplate.insert("TOperator.save", entity);
	}
	
	public void update(TOperator entity) throws SQLException{
		sqlSessionTemplate.update("TOperator.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TOperator.deleteById", id);
	}
}

