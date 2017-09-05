package com.becky.securityboot.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.becky.securityboot.domain.UserDomain;

public interface UserMapper {

	public abstract UserDomain select(@Param("userId") String userId);

	public abstract List<UserDomain> selectList();

	public abstract int update(@Param("userId") String userId, @Param("encodedPassword") String encodedPassword);

}
