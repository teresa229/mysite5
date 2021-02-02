package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.RboardVo;

@Repository
public class RboardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<RboardVo> selectList(){
		System.out.println("rboard[dao]:selectList");
		
		return sqlSession.selectList("rboard.selectList");
	}
	
	//등록
	public int insert(RboardVo boardVo) {
		System.out.println("rboard[dao]:insert");
		
		int count = sqlSession.insert("rboard.insert", boardVo);
		return count;
	}
	
	//삭제
	public int delete(int no) {
		System.out.println("rboard[dao]:delete");
		
		return sqlSession.delete("rboard.delete", no);
	}
	
	//수정
	public int update(RboardVo boardVo) {
		System.out.println("rboard[dao]:update");
		
		return sqlSession.update("rboard.update", boardVo);
	}

	//불러오기
	public RboardVo selectOne(int no) {
		System.out.println("rboard[dao]:selectOne");
		
		RboardVo boardVo = sqlSession.selectOne("rboard.selectOne", no);
		return boardVo;
	}
	
	//조회수+1
	public int updateHit(int no) {
		System.out.println("rboard[dao]:updateHit");
		
		return sqlSession.update("rboard.updateHit", no);
	}
	
	//댓글게시판 댓글쓰기
	public void commentInsert(RboardVo boardVo) {
		System.out.println("rboard[dao]:commentInsert");
		
		sqlSession.insert("rboard.commentInsert", boardVo);
	}
	
	//order_no증가시키기
	public void orderNoUpdate(RboardVo boardVo) {
		System.out.println("rboard[dao]:orderNoUpdate");
		
		sqlSession.update("rboard.orderNoUpdate", boardVo);
	}
}
