/**  
* @Project: xproduct
* @Title: TSysrolePrivService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-16 上午11:08:46
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TSysrolePriv;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSysrolePrivService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-16   上午11:08:46
 */
@Transactional
public class TSysrolePrivService extends BaseService {

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSysrolePriv>  findPage(int pageIndex, int pageSize, TSysrolePriv queryEntity){
		return tsysrolePrivDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysrolePriv find(Long id) throws SQLException{
		return tsysrolePrivDao.find(id);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysrolePriv findByRoleId(Long roleId) throws SQLException{
		return tsysrolePrivDao.findByRoleId(roleId);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<TSysrolePriv> findByRoleIds(String roleIds) throws SQLException{
		return tsysrolePrivDao.findByRoleIds(roleIds);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  	public List<TSysrolePriv> findByPrivId(Long privId) throws SQLException{
  		return tsysrolePrivDao.findByPrivId(privId);
  	}
   
   public void insert(TSysrolePriv entity) throws SQLException{
	   tsysrolePrivDao.insert( entity);
	}
	
	public void update(TSysrolePriv entity) throws SQLException{
		tsysrolePrivDao.update(entity);
	}
	
	public void delete(Long id) throws SQLException{
		tsysrolePrivDao.delete(id);
	}

}

