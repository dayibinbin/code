/**  
* @Project: xproduct
* @Title: TSysprivService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-3-2 下午2:25:38
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

import cn.com.checknull.model.po.TSysmenu;
import cn.com.checknull.model.po.TSyspriv;
import cn.com.checknull.model.po.TSysrolePriv;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSyspriv
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-3-2   下午2:25:38
 */
@Transactional
public class TSysprivService extends BaseService{
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSyspriv>  findPage(int pageIndex, int pageSize, TSyspriv queryEntity){
		return tsysprivDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSyspriv find(Long id) throws SQLException{
		TSyspriv tmpTSyspriv = tsysprivDao.find(id);
		if (tmpTSyspriv != null){
			Long menuId = tmpTSyspriv.getMenuId();
			if (menuId != null){
				TSysmenu tmpTSysmenu = tsysmenuDao.find(menuId);
				tmpTSyspriv.setMenu(tmpTSysmenu);
			}
		}
		return tmpTSyspriv;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<TSyspriv> findByMenuId(Long menuId) throws SQLException{
		List<TSyspriv> tmpListTSyspriv = tsysprivDao.findByMenuId(menuId);
		if (tmpListTSyspriv != null && !tmpListTSyspriv.isEmpty()){
			for (TSyspriv tmpTSyspriv : tmpListTSyspriv){
				Long tmpMenuId;
				TSysmenu tmpTSysmenu;
				if (tmpTSyspriv != null){
					tmpMenuId = tmpTSyspriv.getMenuId();
					if (tmpMenuId != null){
						tmpTSysmenu = tsysmenuDao.find(tmpMenuId);
						tmpTSyspriv.setMenu(tmpTSysmenu);
					}
				}
			}
		}
		return tmpListTSyspriv;
	}
	
	public void insert(TSyspriv entity) throws SQLException{
		if (entity == null) return;
		TSyspriv tmpTSyspriv = tsysprivDao.findByNameAndMenuId(entity.getName(), entity.getMenuId());
		if (tmpTSyspriv != null){
			throw new SQLException("权限名称（" + entity.getName() + "）已被使用！");
		}
		tmpTSyspriv = tsysprivDao.findByCodeAndMenuId(entity.getCode(), entity.getMenuId());
		if (tmpTSyspriv != null){
			throw new SQLException("权限代码（" + entity.getName() + "）已被使用！");
		}
		Long tmpMenuId = entity.getMenuId();
		if(tmpMenuId == null){
			throw new SQLException("未绑定菜单！");
		}
		tsysprivDao.insert(entity);
	}
	
	public void mod(TSyspriv entity) throws SQLException {
		if (entity == null) return;
		TSyspriv tmpTSyspriv = tsysprivDao.findByNameAndMenuId(entity.getName(), entity.getMenuId());
		if (tmpTSyspriv != null && tmpTSyspriv.getId().compareTo(entity.getId()) != 0){
			throw new SQLException("权限名称（" + entity.getName() + "）已被使用");
		}
		tmpTSyspriv = tsysprivDao.findByCodeAndMenuId(entity.getCode(), entity.getMenuId());
		if (tmpTSyspriv != null && tmpTSyspriv.getId().compareTo(entity.getId()) != 0){
			throw new SQLException("权限代码（" + entity.getName() + "）已被使用");
		}
		tsysprivDao.update(entity);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSyspriv entity = find(id);
			if (entity == null) continue;
			List<TSysrolePriv> tmpListTSysrolePriv = tsysrolePrivDao.findByPrivId(id);
			if (tmpListTSysrolePriv != null && !tmpListTSysrolePriv.isEmpty()){
				throw new SQLException("权限代码（" + entity.getName() + "）已经与角色绑定，请先解绑！");
			}
			tsysprivDao.delete(id);
		}
	}
	
	public void bathOn(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSyspriv entity = find(id);
			if (entity == null) continue;
			entity.setStatus(1);
			tsysprivDao.update(entity);
		}
	}

	public void bathOff(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSyspriv entity = find(id);
			if (entity == null) continue;
			entity.setStatus(0);
			tsysprivDao.update(entity);
		}
	}
}

