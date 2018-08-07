/**  
* @Project: xproduct-mybatis
* @Title: TSysprivDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午9:29:27
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.checknull.model.po.TSyspriv;

/**
 * @ClassName TSysprivDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午9:29:27
 */
public class TSysprivDao extends BaseDao<TSyspriv> {
	public TSyspriv find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSyspriv.findById", id);
	}
	
	public List<TSyspriv> findByMenuId(Long menuId) throws SQLException{
		return sqlSessionTemplate.selectList("TSyspriv.findByMenuId", menuId);
	}
	
	public TSyspriv findByNameAndMenuId(String name, Long menuId) throws SQLException{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("menuId", menuId);
		return sqlSessionTemplate.selectOne("TSyspriv.findByNameAndMenuId", parameters);
	}
	
	public TSyspriv findByCodeAndMenuId(String code, Long menuId) throws SQLException{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);
		parameters.put("menuId", menuId);
		return sqlSessionTemplate.selectOne("TSyspriv.findByCodeAndMenuId", parameters);
	}
	
	public void insert(TSyspriv entity) throws SQLException{
		sqlSessionTemplate.insert("TSyspriv.save", entity);
	}
	
	public void update(TSyspriv entity) throws SQLException{
		sqlSessionTemplate.update("TSyspriv.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSyspriv.deleteById", id);
	}
}

