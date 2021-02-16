package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	// 글 전체 가져오기(keyword)
	public List<BoardVo> selectList2(String keyword) {
		System.out.println("[dao]:selectList2");
		System.out.println("keyword=" + keyword);
		
		List<BoardVo> boardList = sqlSession.selectList("board.selectList2", keyword);
		//System.out.println("boardList="+boardList);
		
		return boardList; //null ->boardList 변경하고 있다.
		
		//방법 2 : return sqlSession.selectList("board.selectList2", keyword);
	}
	
	// 글 전체 가져오기(keyword+ 페이징)
	public List<BoardVo> selectList3(String keyword, int startRNum, int endRNum) { // keyword,startRNum, endRNum 3개는 묶어서 여기서만 사용할 것 같다.. map으로 묶어주기..
		System.out.println("[dao]:selectList3");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startRNum", startRNum);
		map.put("endRNum", endRNum);
		System.out.println(map);
		
		return sqlSession.selectList("board.selectList3", map);
	}
	
	
	//전체글 갯수 가져오기
	public int selectTotalCnt(String keyword) {
		System.out.println("[dao]:selectTotalCnt");
		
		return sqlSession.selectOne("board.selectTotalCnt",keyword);
	}
	
	//등록
	public int insert(BoardVo boardVo) {
		System.out.println("[dao]:insert");
		
		int count =sqlSession.insert("board.insert", boardVo);
		return count;
	}
	
	//삭제
	public int delete(int no) {
		System.out.println("[dao]:delete");
		
		return sqlSession.delete("board.delete", no);
	}
	
	//수정
	public int update(BoardVo boardVo) {
		System.out.println("[dao]:update");
		
		return sqlSession.update("board.update", boardVo);
	}

	//불러오기
	public BoardVo selectOne(int no) {
		System.out.println("[dao]:selectOne");
		
		BoardVo boardVo = sqlSession.selectOne("board.selectOne", no);
		return boardVo;
	}
	
	//조회수+1
	public int updateHit(int no) {
		System.out.println("[dao]:updateHit");
		
		return sqlSession.update("board.updateHit", no);
	}
}
