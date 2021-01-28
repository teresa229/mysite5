package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	
	@Autowired
	private UserDao userDao;
	
	//회원가입
	public int join(UserVo userVo) {
		System.out.println("(userService):join()");
		/*
		 int count = userDao.insert(userVo);
		 return count;
		 */
		
		return userDao.insert(userVo);
	}
	//로그인
	public UserVo login(UserVo userVo) {
		System.out.println("(userService):login()");
		
		return userDao.selectUser(userVo);
	}
	//회원정보 수정폼
	public UserVo modifyForm(int no) {
		System.out.println("(userService):modifyForm()");
		
		/*
		 * UserVo userVo = userDao.selectOne(no); 
		 * return userVo;
		 */
		
		return userDao.selectUser(no);
	}
	
	//회원정보 수정
	public int modify(UserVo userVo) {
		System.out.println("(userService):modify()");
		
		return userDao.modify(userVo);   //친숙한 이름으로 update를 넣어주는 것이 어떻겠니?
	}
}
