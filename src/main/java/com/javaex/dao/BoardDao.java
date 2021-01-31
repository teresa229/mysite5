package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<BoardVo> selectList(){
		System.out.println("[dao]:selectList");
		
		return sqlSession.selectList("board.selectList");
	}
	
	//등록
	public int insert(BoardVo boardVo) {
		System.out.println("[dao]:insert");
		System.out.println(boardVo.toString());
		
		int count =sqlSession.insert("board.insert", boardVo);
		return count;
	}
	
	//삭제
	public int delete(int no) {
		System.out.println("[dao]:delete");
		
		int count = sqlSession.delete("board.delete", no);
		System.out.println(count);
		return count;
	}
	
	//수정
	public int update(BoardVo boardVo) {
		System.out.println("[dao]:update");
		System.out.println("[dao]:update"+boardVo.toString());
		
		int count = sqlSession.update("board.update", boardVo);
		System.out.println(count);
		return count;
	}

	//불러오기
	public BoardVo selectOne(int no) {
		System.out.println("[dao]:selectOne");
		return sqlSession.selectOne("board.selectOne", no);
	}
	
	//조회수+1
	public int updateHit(int no) {
		System.out.println("[dao]:updateHit");
		return sqlSession.update("board.updateHit", no);
	}
}
