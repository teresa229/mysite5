package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao{

	@Autowired
	private SqlSession sqlSession;
	
	//방명록 리스트
	public List<GuestVo> guestList() {
		System.out.println("[GuestDao] guestList()");
		
		return sqlSession.selectList("guestbook.selectList");
	}
	
	//방명록 작성
	public void insert(GuestVo guestVo) {
		System.out.println("[GuestDao] insert()");
		System.out.println(guestVo.toString());
		sqlSession.insert("guestbook.insert", guestVo);
	}
	
	//방명록 삭제
	public int delete(GuestVo guestVo) {
		System.out.println("[GuestDao] delete()");
		
		int count = sqlSession.delete("guestbook.delete", guestVo);
		return count;
	}
	
	//21.02.09 저장 + 가져오기selectOne
	//글저장(selectkey)
	public void insertSelectKey(GuestVo guestVo) {
		System.out.println("[GuestDao] insertSelectKey()");
		
		System.out.println("dao: xml실행 전-->"+guestVo);
		sqlSession.insert("guestbook.insertSelectKey", guestVo);
		System.out.println("dao: xml실행 후-->"+guestVo);
		System.out.println(guestVo.getNo());
	}
	
	//글 1개 가져오기
	public GuestVo selectOne(int no) {
		System.out.println("[GuestDao] selectOne()");
		return sqlSession.selectOne("guestbook.select", no);
	}
}
