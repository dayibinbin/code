/**  
* @Project: xproduct-mybatis
* @Title: TSysmenuDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午8:49:22
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import cn.com.checknull.model.po.TSysmenu;

/**
 * @ClassName TSysmenuDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午8:49:22
 */
public class TSysmenuDao extends BaseDao<TSysmenu> {
	
	public TSysmenu find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysmenu.findById", id);
	}
	
	public List<TSysmenu> find(String ids) throws SQLException{
		String[] array = ids.split(",");
		Long[] arrayIds = new Long[array.length];
		for (int i = 0; i < array.length; i++){
			arrayIds[i] = NumberUtils.toLong(array[i]);
		}
		return sqlSessionTemplate.selectList("TSysmenu.findByIds", arrayIds);
	}
	
	public List<TSysmenu> findByParentId(Long parentId) throws SQLException{
		return sqlSessionTemplate.selectList("TSysmenu.findByParentId", parentId);
	}
	
	public void insert(TSysmenu entity) throws SQLException{
		sqlSessionTemplate.insert("TSysmenu.save", entity);
	}
	
	public void update(TSysmenu entity) throws SQLException{
		sqlSessionTemplate.update("TSysmenu.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSysmenu.deleteById", id);
	}
}

