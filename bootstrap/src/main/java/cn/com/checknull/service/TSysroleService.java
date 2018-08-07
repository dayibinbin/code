/**  
* @Project: xproduct
* @Title: TSysroleService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-13 下午3:01:21
* @Copyright: 2015 check_null Reserved.
* @version v1.0  
*/

package cn.com.checknull.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.checknull.model.po.TOperatorRole;
import cn.com.checknull.model.po.TSysmenu;
import cn.com.checknull.model.po.TSyspriv;
import cn.com.checknull.model.po.TSysrole;
import cn.com.checknull.model.po.TSysroleMenu;
import cn.com.checknull.model.po.TSysrolePriv;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSysroleService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-13   下午3:01:21
 */
@Transactional
public class TSysroleService extends BaseService{

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSysrole>  findPage(int pageIndex, int pageSize, TSysrole queryEntity){
		return tsysroleDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysrole find(Long id) throws SQLException{
		return tsysroleDao.find(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<TSysrole> find(String ids) throws SQLException{
		return tsysroleDao.find(ids);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void insert(TSysrole entity, String menuIds, String privIds) throws SQLException {
		if (entity == null) return;
		TSysrole tmpTSysrole = tsysroleDao.findByName(entity.getName());
		if (tmpTSysrole != null){
			throw new SQLException("角色（" + entity.getName() + "）已被使用");
		}
		tsysroleDao.insert(entity);
		
		if (!StringUtils.isEmpty(menuIds)) {
			String[] tmpMenuIdArray = menuIds.split(",");
			for (String tmpMenuId : tmpMenuIdArray){
				Long id = NumberUtils.toLong(tmpMenuId);
				TSysmenu tmpTSysmenu = tsysmenuDao.find(id);
				if (tmpTSysmenu == null){
					throw new SQLException("菜单（" + id + "）不存在");
				}
			}
		}
		
		TSysroleMenu tmpTSysroleMenu = new TSysroleMenu();
		tmpTSysroleMenu.setMenuIds(menuIds);
		tmpTSysroleMenu.setRoleId(entity.getId());
		tsysroleMenuDao.insert(tmpTSysroleMenu);
		
		if (!StringUtils.isEmpty(privIds)){
			String[] tmpPrivIdArray = privIds.split(",");
			for (String tmpPrivId : tmpPrivIdArray){
				Long id = NumberUtils.toLong(tmpPrivId);
				TSyspriv tmpTSyspriv = tsysprivDao.find(id);
				if (tmpTSyspriv == null){
					throw new SQLException("权限（" + id + "）不存在");
				}
			}
		}
		
		TSysrolePriv tmpTSysrolePriv = new TSysrolePriv();
		tmpTSysrolePriv.setPrivIds(privIds);
		tmpTSysrolePriv.setRoleId(entity.getId());
		tsysrolePrivDao.insert(tmpTSysrolePriv);
	}
	@Transactional(rollbackFor = Exception.class)
	public void mod(TSysrole entity, String menuIds, String privIds) throws SQLException {
		if (entity == null) return;
		Long id = entity.getId();
		if (find(id) == null) return;
		TSysrole tmpTSysrole = tsysroleDao.findByName(entity.getName());
		if (tmpTSysrole != null && tmpTSysrole.getId().compareTo(id) != 0){
			throw new SQLException("角色（" + entity.getName() + "）已被使用");
		}
		tsysroleDao.update(entity);
		
		if (!StringUtils.isEmpty(menuIds)){
			String[] tmpMenuIdArray = menuIds.split(",");
			for (String tmpMenuId : tmpMenuIdArray){
				Long tmpId = NumberUtils.toLong(tmpMenuId);
				TSysmenu tmpTSysmenu = tsysmenuDao.find(tmpId);
				if (tmpTSysmenu == null){
					throw new SQLException("菜单（" + tmpId + "）不存在");
				}
			}
		}
		
		TSysroleMenu tmpTSysroleMenu = tsysroleMenuDao.findByRoleId(id);
		if (tmpTSysroleMenu == null){
			tmpTSysroleMenu = new TSysroleMenu();
			tmpTSysroleMenu.setMenuIds(menuIds);
			tmpTSysroleMenu.setRoleId(entity.getId());
			tsysroleMenuDao.insert(tmpTSysroleMenu);
		}else{
			tmpTSysroleMenu.setMenuIds(menuIds);
			tsysroleMenuDao.update(tmpTSysroleMenu);
		}
		
		if (!StringUtils.isEmpty(privIds)) {
			String[] tmpPrivIdArray = privIds.split(",");
			for (String tmpPrivId : tmpPrivIdArray){
				Long tmpId = NumberUtils.toLong(tmpPrivId);
				TSyspriv tmpTSyspriv = tsysprivDao.find(tmpId);
				if (tmpTSyspriv == null){
					throw new SQLException("权限（" + tmpId + "）不存在");
				}
			}
		}
		
		TSysrolePriv tmpTSysrolePriv = tsysrolePrivDao.findByRoleId(id);
		if (tmpTSysrolePriv == null){
			tmpTSysrolePriv = new TSysrolePriv();
			tmpTSysrolePriv.setPrivIds(privIds);
			tmpTSysrolePriv.setRoleId(entity.getId());
			tsysrolePrivDao.insert(tmpTSysrolePriv);
		}else{
			tmpTSysrolePriv.setPrivIds(privIds);
			tsysrolePrivDao.update(tmpTSysrolePriv);
		}
	}
	@Transactional(rollbackFor = Exception.class)
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysrole entity = find(id);
			if (entity == null) continue;
			List<TOperatorRole> tmpListTOperatorRole = toperatorRoleDao.findByRoleId(id);
			if (tmpListTOperatorRole != null && !tmpListTOperatorRole.isEmpty()){
				throw new SQLException("角色（" + entity.getName() + "）已经与操作员绑定，请先解绑！");
			}
			tsysroleDao.delete(id);
			
			TSysroleMenu tmpTSysroleMenu = tsysroleMenuDao.findByRoleId(id);
			if (tmpTSysroleMenu != null){
				tsysroleMenuDao.delete(tmpTSysroleMenu.getId());
			}
			
			TSysrolePriv tmpTSysrolePriv = tsysrolePrivDao.findByRoleId(id);
			if (tmpTSysrolePriv != null){
				tsysrolePrivDao.delete(tmpTSysrolePriv.getId());
			}
		}
	}
	
	public void bathOn(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysrole entity = find(id);
			if (entity == null) continue;
			entity.setStatus(1);
			tsysroleDao.update(entity);
		}
	}

	public void bathOff(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysrole entity = find(id);
			if (entity == null) continue;
			entity.setStatus(0);
			tsysroleDao.update(entity);
		}
	}

}

