/**  
* @Project: xproduct-mybatis
* @Title: TSysroleDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午10:00:42
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import cn.com.checknull.model.po.TSysrole;

/**
 * @ClassName TSysroleDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午10:00:42
 */
public class TSysroleDao extends BaseDao<TSysrole> {
	
	public TSysrole find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysrole.findById", id);
	}
	
	public List<TSysrole> find(String ids) throws SQLException{
		String[] array = ids.split(",");
		Long[] arrayIds = new Long[array.length];
		for (int i = 0; i < array.length; i++){
			arrayIds[i] = NumberUtils.toLong(array[i]);
		}
		return sqlSessionTemplate.selectList("TSysrole.findByIds", arrayIds);
	}
	
	public TSysrole findByName(String name) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysrole.findByName", name);
	}
	
	public void insert(TSysrole entity) throws SQLException{
		sqlSessionTemplate.insert("TSysrole.save", entity);
	}
	
	public void update(TSysrole entity) throws SQLException{
		sqlSessionTemplate.update("TSysrole.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSysrole.deleteById", id);
	}
}

