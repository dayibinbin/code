package cn.com.checknull.model.vo; 

import java.util.List;

public class ZTree {
	private Object id;
	private String name;
	private boolean open;
	private boolean chkDisabled;
	private List<ZTree> children;
	
	public ZTree(Object id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ZTree(Object id, String name, boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.open = open;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ZTree> getChildren() {
		return children;
	}
	public void setChildren(List<ZTree> children) {
		this.children = children;
	}
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	
	
}
 