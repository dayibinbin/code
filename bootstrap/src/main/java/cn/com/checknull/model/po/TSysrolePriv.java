package cn.com.checknull.model.po;

import org.apache.commons.lang3.StringUtils;


// Generated 2015-3-13 14:52:20 by Hibernate Tools 3.4.0.CR1

/**
 * TSysrolePriv generated by hbm2java
 */
public class TSysrolePriv extends QueryEntity implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 4320232863771114134L;
	private Long id;
	private Long roleId;
	private String privIds;

	public TSysrolePriv() {
	}

	public TSysrolePriv(Long roleId, String privIds) {
		this.roleId = roleId;
		this.privIds = privIds;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getPrivIds() {
		return this.privIds;
	}

	public void setPrivIds(String privIds) {
		this.privIds = privIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TSysrolePriv other = (TSysrolePriv) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public void setSort(String sort) {
		
		if (StringUtils.isEmpty(sort) || "id".equals(sort)){
			super.setSort("ID");
		}else if ("roleId".equals(sort)){
			super.setSort("ROLE_ID");
		}else if ("privIds".equals(sort)){
			super.setSort("PRIV_IDS");
		}else{
			super.setSort("ID");
		}
	}
}