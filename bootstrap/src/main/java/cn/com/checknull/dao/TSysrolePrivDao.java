/**  
* @Project: xproduct-mybatis
* @Title: TSysrolePrivDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午10:27:56
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import cn.com.checknull.model.po.TSysrolePriv;

/**
 * @ClassName TSysrolePrivDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午10:27:56
 */
public class TSysrolePrivDao extends BaseDao<TSysrolePriv> {
	public TSysrolePriv find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysrolePriv.findById", id);
	}
	
	public TSysrolePriv findByRoleId(Long roleId) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysrolePriv.findByRoleId", roleId);
	}
	
	public List<TSysrolePriv> findByRoleIds(String roleIds) throws SQLException{
		String[] array = roleIds.split(",");
		Long[] arrayRoleIds = new Long[array.length];
		for (int i = 0; i < array.length; i++){
			arrayRoleIds[i] = NumberUtils.toLong(array[i]);
		}
		return sqlSessionTemplate.selectList("TSysrolePriv.findByRoleIds", arrayRoleIds);
	}
	
	public List<TSysrolePriv> findByPrivId(Long privId) throws SQLException{
		String tmpPrivId = "," + privId + ",";
		return sqlSessionTemplate.selectList("TSysrolePriv.findByPrivId", tmpPrivId);
	}
	
	public void insert(TSysrolePriv entity) throws SQLException{
		sqlSessionTemplate.insert("TSysrolePriv.save", entity);
	}
	
	public void update(TSysrolePriv entity) throws SQLException{
		sqlSessionTemplate.update("TSysrolePriv.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSysrolePriv.deleteById", id);
	}
}

