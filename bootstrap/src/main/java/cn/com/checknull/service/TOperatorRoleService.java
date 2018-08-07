/**  
* @Project: xproduct
* @Title: TOperatorRoleService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-17 上午9:52:19
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TOperatorRole;

/**
 * @ClassName TOperatorRoleService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-17   上午9:52:19
 */
@Transactional
public class TOperatorRoleService extends BaseService {
	 
	   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
		public TOperatorRole find(Long id) throws SQLException{
			return toperatorRoleDao.find(id);
		}
	   
	   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
		public TOperatorRole findByOperatorId(Long operatorId) throws SQLException{
			return toperatorRoleDao.findByOperatorId(operatorId);
		}
	   
	   @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
		public List<TOperatorRole> findByRoleId(Long roleId) throws SQLException{
			return toperatorRoleDao.findByRoleId(roleId);
		}
	   
	   public void insert(TOperatorRole entity) throws SQLException{
		   toperatorRoleDao.insert( entity);
		}
		
		public void update(TOperatorRole entity) throws SQLException{
			toperatorRoleDao.update(entity);
		}
		
		public void delete(Long id) throws SQLException{
			toperatorRoleDao.delete(id);
		}
}

