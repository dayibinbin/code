package cn.com.checknull.model.po;

// Generated 2015-3-13 14:52:20 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import cn.com.checknull.model.serializer.DateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * TSysrole generated by hbm2java
 */
public class TSysrole extends QueryEntity implements java.io.Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7532280530805867967L;
	private Long id;
	private String name;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = DateSerializer.class)
	private Date createTime = new Date();
	private Integer status;

	public TSysrole() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		TSysrole other = (TSysrole) obj;
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
		}else if ("name".equals(sort)){
			super.setSort("NAME");
		}else if ("description".equals(sort)){
			super.setSort("DESCRIPTION");
		}else if ("createTime".equals(sort)){
			super.setSort("CREATE_TIME");
		}else if ("status".equals(sort)){
			super.setSort("STATUS");
		}else{
			super.setSort("ID");
		}
	}
}