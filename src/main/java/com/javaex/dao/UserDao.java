package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	//회원가입 : db입장에서는 회원정보 저장
	public int insert(UserVo userVo){    //대부분 int로 불러오고 있으니~int
		System.out.println("[dao]:insert");
		System.out.println("[dao]:insert" + userVo.toString());
		
		int count = sqlSession.insert("user.insert",userVo);
		System.out.println("user.insert count:"+count);
		return count;
		
		//return sqlSession.insert("user.insert",userVo);
	}
	//로그인 : 회원정보 가져오기.. 결과물을 모를때는 일단 void
	public UserVo selectUser(UserVo userVo) {      /* selectUser(UserVo userVo), selectOne(int no) //이름을 같게 적어주어도 괜찮다. select*/
		System.out.println("[dao]:selectUser");
		System.out.println("[dao]:selectUser" + userVo.toString());
		
		UserVo vo = sqlSession.selectOne("user.selectUser",userVo);        //UserVo userVo하면 이름이 겹쳐지므로 UserVo vo 
		System.out.println(vo.toString());
		
		return vo; 
		//return sqlSession.selectOne("user.selectUser",userVo);
	}
	//회원 정보 가져오기 
	public UserVo selectUser(int no) {
		System.out.println("[dao]:selectOne");
		System.out.println(no);
		
		return sqlSession.selectOne("user.selectOne", no);
	}
	
	
	//회원정보 수정
	public int modify(UserVo userVo) {
		System.out.println("[dao]:modify");
		System.out.println("[dao]:modify" + userVo.toString()); //[dao]:modifyUserVo [no=23, id=null, password=1234, name=김경아 마지막, gender=female]
		
		/*
		 * int count = sqlSession.update("user.update", userVo); return count;
		 */
		
		return sqlSession.update("user.update", userVo);
	}
}
