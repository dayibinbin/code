/**  
* @Project: xproduct-mybatis
* @Title: TLogOperateDao.java
* @Package cn.com.checknull.dao
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-5-31 下午4:21:03
* @Copyright: 2015 check_null All Rights Reserved.
* @version v1.0  
*/

package cn.com.checknull.dao;

import java.sql.SQLException;

import cn.com.checknull.model.po.TLogOperate;

/**
 * @ClassName TLogOperateDao
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-5-31   下午4:21:03
 */
public class TLogOperateDao extends BaseDao<TLogOperate> {
	
	public TLogOperate find(Long id) throws SQLException{
		return sqlSessionTemplate.selectOne("TLogOperate.findById", id);
	}
	
	public void insert(TLogOperate entity) throws SQLException{
		sqlSessionTemplate.insert("TLogOperate.save", entity);
	}
	
	public void delete(Long id) throws SQLException {
		sqlSessionTemplate.delete("TLogOperate.deleteById", id);
	}
}

