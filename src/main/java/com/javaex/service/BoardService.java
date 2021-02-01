package com.javaex.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private HttpSession session;
	
	//리스트
	public List<BoardVo> list() {
		System.out.println("[service]:selectList");
		
		List<BoardVo> boardList = boardDao.selectList();
		
		//return boardDao.selectList();
		return boardList;
	}
	
	//등록
	public int write(BoardVo boardVo) {
		System.out.println("[service]:write");
		
		//세션에서 사용자 정보 가져오기
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		//Vo에 no담기
		int no = authVo.getNo();
		boardVo.setUserNo(no);   //setUserNo
		
		int count = boardDao.insert(boardVo);
		System.out.println(count);
		return count;
	}
	
	//삭제
	public int remove(int no) {
		System.out.println("[service]:remove");
		return boardDao.delete(no);
	}
	
	//수정
	public int modify(BoardVo boardVo) {
		System.out.println("[service]:modify");
		
		return boardDao.update(boardVo);
	}
	
	//수정폼
	public BoardVo modifyForm(int no) {
		System.out.println("[service]:modifyForm");
		
		return boardDao.selectOne(no);
	}
	
	//글보기
	public BoardVo read(int no) {
		System.out.println("[service]:read");
		
		BoardVo bVo = boardDao.selectOne(no);
		return bVo;
	}
}
