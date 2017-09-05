package com.becky.securityboot.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MenuMapper {

	public abstract List<Map<?, ?>> selectListByGroup(@Param(value="groupSeq") Integer groupSeq);

	public abstract List<Map<?, ?>> selectMenuList();

	public abstract List<?> selectUserGroupList(@Param(value="useFlag") Character useFlag);

	public abstract List<Map<?, ?>> selectMenuNotAuth();

}
