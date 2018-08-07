/**  
* @Project: xproduct
* @Title: TSysmenuService.java
* @Package cn.com.checknull.service
* @Description: TODO
* @author zhangguangbin zhanggbdayi@163.com
* @date 2015-2-28 下午2:04:05
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
import cn.com.checknull.model.po.TSysroleMenu;
import cn.com.checknull.model.vo.QueryResult;

/**
 * @ClassName TSysmenuService
 * @Description TODO
 * @author zhangguangbin zhanggbdayi@163.com
 * @date 2015-2-28   下午2:04:05
 */
@Transactional
public class TSysmenuService extends BaseService {

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<TSysmenu>  findPage(int pageIndex, int pageSize, TSysmenu queryEntity){
		return tsysmenuDao.findPage(pageIndex, pageSize, queryEntity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TSysmenu find(Long id) throws SQLException{
		TSysmenu tmpTSysmenu = tsysmenuDao.find(id);
		if (tmpTSysmenu != null){
			Long parentId = tmpTSysmenu.getParentId();
			if (parentId != null){
				TSysmenu tmpParentTSysmenu = tsysmenuDao.find(parentId);
				tmpTSysmenu.setParentMenu(tmpParentTSysmenu);
			}
		}
		return tmpTSysmenu;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<TSysmenu> find(String ids) throws SQLException{
		List<TSysmenu> tmpListTSysmenu = tsysmenuDao.find(ids);
		if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
			Long parentId;
			TSysmenu tmpParentTSysmenu;
			for (TSysmenu tmpTSysmenu : tmpListTSysmenu){
				parentId = tmpTSysmenu.getParentId();
				if (parentId != null){
					tmpParentTSysmenu = tsysmenuDao.find(parentId);
					tmpTSysmenu.setParentMenu(tmpParentTSysmenu);
				}
			}
		}
		return tmpListTSysmenu;
	}
	
	public void insert(TSysmenu entity) throws SQLException{
		if (entity == null) return;
		TSysmenu tmpParent = entity.getParentMenu();
		if (tmpParent != null){
			entity.setParentId(tmpParent.getId());
		}
		tsysmenuDao.insert(entity);
	}
	
	public void mod(TSysmenu entity) throws SQLException {
		if (entity == null) return;
		TSysmenu tmpParent = entity.getParentMenu();
		if (tmpParent != null){
			entity.setParentId(tmpParent.getId());
		}
		tsysmenuDao.update(entity);
	}
	@Transactional(rollbackFor = Exception.class)
	public void bathDelete(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysmenu entity = find(id);
			if (entity == null) continue;
			List<TSysmenu> tmpListTSysmenu = tsysmenuDao.findByParentId(id);
			if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
				throw new SQLException("菜单（" + entity.getName() + "）拥有子菜单，请先解绑！");
			}
			List<TSyspriv> tmpListTSyspriv = tsysprivDao.findByMenuId(id);
			if (tmpListTSyspriv != null && !tmpListTSyspriv.isEmpty()){
				throw new SQLException("菜单（" + entity.getName() + "）已经与权限绑定，请先解绑！");
			}
			 List<TSysroleMenu> tmpListTSysroleMenu = tsysroleMenuDao.findByMenuId(id);
			if (tmpListTSysroleMenu != null && !tmpListTSysroleMenu.isEmpty()){
				throw new SQLException("菜单（" + entity.getName() + "）已经与角色绑定，请先解绑！");
			}
			tsysmenuDao.delete(id);
		}
	}
	
	public void bathOn(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysmenu entity = find(id);
			if (entity == null) continue;
			List<TSysmenu> tmpListTSysmenu = tsysmenuDao.findByParentId(id);
			if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
				for (TSysmenu tmpTSysmenu : tmpListTSysmenu){
					tmpTSysmenu.setStatus(1);
					tsysmenuDao.update(tmpTSysmenu);
				}
			}
			entity.setStatus(1);
			tsysmenuDao.update(entity);
		}
	}

	public void bathOff(String ids) throws SQLException {
		if (StringUtils.isEmpty(ids)) return;
		String[] array = ids.split(",");
		for (String str : array){
			Long id = NumberUtils.toLong(str, 0L);
			TSysmenu entity = find(id);
			if (entity == null) continue;
			List<TSysmenu> tmpListTSysmenu = tsysmenuDao.findByParentId(id);
			if (tmpListTSysmenu != null && !tmpListTSysmenu.isEmpty()){
				for (TSysmenu tmpTSysmenu : tmpListTSysmenu){
					tmpTSysmenu.setStatus(0);
					tsysmenuDao.update(tmpTSysmenu);
				}
			}
			entity.setStatus(0);
			tsysmenuDao.update(entity);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void sort(String sorts) throws SQLException {
		if (StringUtils.isEmpty(sorts)) return;
		String[] array1 = sorts.split(",");
		for (String str : array1){
			if (StringUtils.isEmpty(str)) continue;
			String[] array2 = str.split("-");
			if (array2.length != 2) continue;
			Long id = NumberUtils.toLong(array2[0], 0L);
			Integer sequence = NumberUtils.toInt(array2[1], 10);
			TSysmenu entity = find(id);
			if (entity == null) continue;
			entity.setSequence(sequence);
			tsysmenuDao.update(entity);
		}
	}
}

