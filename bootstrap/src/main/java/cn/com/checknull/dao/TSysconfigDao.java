/**  
* @Project: xproduct-mybatis
* @Title: TSysconfigDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午6:18:03
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;

import cn.com.checknull.model.po.TSysconfig;

/**
 * @ClassName TSysconfigDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午6:18:03
 */
public class TSysconfigDao extends BaseDao<TSysconfig> {
	
	public TSysconfig find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysconfig.findById", id);
	}
	
	public TSysconfig findByParamCode(String paramCode) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysconfig.findByParamCode", paramCode);
	}
	
	public TSysconfig findByParamName(String paramName) throws SQLException{
		return sqlSessionTemplate.selectOne("TSysconfig.findByParamName", paramName);
	}
	
	public void insert(TSysconfig entity) throws SQLException{
		sqlSessionTemplate.insert("TSysconfig.save", entity);
	}
	
	public void update(TSysconfig entity) throws SQLException{
		sqlSessionTemplate.update("TSysconfig.update", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TSysconfig.deleteById", id);
	}
}

