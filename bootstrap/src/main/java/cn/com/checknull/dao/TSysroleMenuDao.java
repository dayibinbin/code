/**  
* @Project: xproduct-mybatis
* @Title: TSysroleMenuDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午10:26:31
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import cn.com.checknull.model.po.TSysroleMenu;

/**
 * @ClassName TSysroleMenuDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午10:26:31
 */
public class TSysroleMenuDao extends BaseDao<TSysroleMenu> {
	public TSysroleMenu find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysroleMenu.findById", id);
	}
	
	public TSysroleMenu findByRoleId(Long roleId) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysroleMenu.findByRoleId", roleId);
	}
	
	public List<TSysroleMenu> findByRoleIds(String roleIds) throws SQLException{
		String[] array = roleIds.split(",");
		Long[] arrayRoleIds = new Long[array.length];
		for (int i = 0; i < array.length; i++){
			arrayRoleIds[i] = NumberUtils.toLong(array[i]);
		}
		return sqlSessionTemplate.selectList("TSysroleMenu.findByRoleIds", arrayRoleIds);
	}
	
	public List<TSysroleMenu> findByMenuId(Long menuId) throws SQLException{
		String tmpMenuId = "," + menuId + ",";
		return sqlSessionTemplate.selectList("TSysroleMenu.findByMenuId", tmpMenuId);
	}
	
	public void insert(TSysroleMenu entity) throws SQLException{
		sqlSessionTemplate.insert("TSysroleMenu.save", entity);
	}
	
	public void update(TSysroleMenu entity) throws SQLException{
		sqlSessionTemplate.update("TSysroleMenu.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSysroleMenu.deleteById", id);
	}
}

