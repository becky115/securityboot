package com.becky.securityboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.becky.securityboot.domain.UserDomain;
import com.becky.securityboot.mappers.UserMapper;

@Service
public class UserService implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	
	private static final String USER_SESSION = "USER_SESSION";
	
//	private SqlSession sqlSession;
//	public void setSqlSession(SqlSessionTemplate sqlSession) {
//		this.sqlSession = sqlSession;
//	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername" + userId);
		UserDomain user = userMapper.select(userId);
		String userName = user.getUserId();
		String userPass = user.getPasswd();
		Character lockFlag = user.getLockFlag();
		logger.info(user.toString());
		
		Boolean enabled = true;
		Boolean accountNonExpired = true;
		Boolean credentialsNonExpired  = true;
		Boolean accountNonLocked = lockFlag.equals('Y') ? false:true;
		System.out.println("userPass" + (userPass));
		
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		String[] groupAuthority = user.getGroupAuthority().toString().split(",");
		for(String role:groupAuthority){
			authorities.add(new SimpleGrantedAuthority(role));
		}
		UserDetails userDetail = new User(userName, userPass, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		System.out.println("userDetail" + userDetail.getPassword());
		
		logger.debug(user.toString());
	//org.springframework.security.authentication.BadCredentialsException:
		return userDetail;
	}
	
	
	public UserDomain findUserById(String userId) {
		return userMapper.select(userId);
	}

	public List<UserDomain> findAllUserList() {
		return userMapper.selectList();
	}

	public int update(String userId, String encodedPassword) {
		return userMapper.update(userId, encodedPassword);
	}
	
	
	public UserDomain getUserDomain(HttpSession session){
		 return (UserDomain) session.getAttribute(USER_SESSION); 
	}
	
	public void setUserDomain(HttpSession session, UserDomain userDomain){
		session.setAttribute(USER_SESSION, userDomain); 
	}
}

