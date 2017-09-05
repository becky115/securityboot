package com.becky.securityboot.domain;

public class UserDomain {
//extends User
//	public UserDomain(String username, String password, boolean enabled, boolean accountNonExpired,
//			boolean credentialsNonExpired, boolean accountNonLocked,
//			Collection<? extends GrantedAuthority> authorities) {
//		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//	}
	
	private String userId;
	private String userName;
	private String passwd;
	private Character lockFlag;
	private Integer groupSeq;
	private String groupName;
	private String groupAuthority;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Character getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Character lockFlag) {
		this.lockFlag = lockFlag;
	}
	public Integer getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupAuthority() {
		return groupAuthority;
	}
	public void setGroupAuthority(String groupAuthority) {
		this.groupAuthority = groupAuthority;
	}
	

}
