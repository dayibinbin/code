/**  
* @Project: xproduct-mybatis
* @Title: TOperatorRoleDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午4:27:41
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;

import cn.com.checknull.model.po.TOperatorRole;

/**
 * @ClassName TOperatorRoleDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午4:27:41
 */
public class TOperatorRoleDao extends BaseDao<TOperatorRole> {
	
	public TOperatorRole find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TOperatorRole.findById", id);
	}
	
	public TOperatorRole findByOperatorId(Long operatorId) throws SQLException{
		return sqlSessionTemplate.selectOne("TOperatorRole.findByOperatorId", operatorId);
	}
	
	public List<TOperatorRole> findByRoleId(Long roleId) throws SQLException{
		String tmpRoleId = "," + roleId + ",";
		return sqlSessionTemplate.selectList("TOperatorRole.findByRoleId", tmpRoleId);
	}
	
	public void insert(TOperatorRole entity) throws SQLException{
		sqlSessionTemplate.insert("TOperatorRole.save", entity);
	}
	
	public void update(TOperatorRole entity) throws SQLException{
		sqlSessionTemplate.update("TOperatorRole.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TOperatorRole.deleteById", id);
	}
}

