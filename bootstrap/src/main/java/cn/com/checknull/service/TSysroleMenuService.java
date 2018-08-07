/**  
* @Project: xproduct
* @Title: TSysroleMenuService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-16 上午11:07:36
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TSysroleMenu;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSysroleMenuService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-16   上午11:07:36
 */
@Transactional
public class TSysroleMenuService extends BaseService{

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSysroleMenu>  findPage(int pageIndex, int pageSize, TSysroleMenu queryEntity){
		return tsysroleMenuDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysroleMenu find(Long id) throws SQLException{
		return tsysroleMenuDao.find(id);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysroleMenu findByRoleId(Long roleId) throws SQLException{
		return tsysroleMenuDao.findByRoleId(roleId);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<TSysroleMenu> findByRoleIds(String roleIds) throws SQLException{
		return tsysroleMenuDao.findByRoleIds(roleIds);
	}
   
   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
  	public List<TSysroleMenu> findByMenuId(Long menuId) throws SQLException{
  		return tsysroleMenuDao.findByMenuId(menuId);
  	}
   
   public void insert(TSysroleMenu entity) throws SQLException{
	   tsysroleMenuDao.insert( entity);
	}
	
	public void update(TSysroleMenu entity) throws SQLException{
		tsysroleMenuDao.update(entity);
	}
	
	public void delete(Long id) throws SQLException{
		tsysroleMenuDao.delete(id);
	}

}

