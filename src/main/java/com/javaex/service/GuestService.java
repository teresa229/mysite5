package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {
	
	@Autowired
	private GuestDao guestDao;
	
	//방명록 리스트 
	public List<GuestVo> guestList() {
		System.out.println("(GuestService):guestList()");
		
		return guestDao.guestList();
	}
	
	//방명록 작성
	public void insert(GuestVo guestVo) {
		System.out.println("(GuestService):insert()");
		
		guestDao.insert(guestVo);
	}
	
	//방명록 삭제
	public int delete(GuestVo guestVo) {
		System.out.println("(GuestService):delete()");
		
		int count = guestDao.delete(guestVo);
		return count;
	}
	
	//21.02.09 저장 + 가져오기selectOne
	/* ajax 글 저장 + 저장된 글 리턴*/
	public GuestVo writeResultVo(GuestVo guestVo) {    
		//글저장
		
		//int no = guestDao.insertSelectKey(guestVo);
		System.out.println("service: xml실행 전-->"+guestVo);
		guestDao.insertSelectKey(guestVo);
		System.out.println("service: xml실행 후-->"+guestVo);
		int no = guestVo.getNo();
		
		//글 1개 가져오기
		GuestVo vo = guestDao.selectOne(no);
		System.out.println(vo);
		
		return vo;
		
		//방금 저장한 글 조회하기 -- 글 번호를 어떻게 알아올 것인가? 방법 : 최근 글 가져오기, no가 크거나 날짜가 최그 정보-- 하지만 사용자가 많으면 문제가 생긴다. 저장하는 사이에 누군가가 또 저장을 하면 최근 글이 내 글이 아니다.
		//최근글 -> 방금 저장된 글이 아닐 수도 있다.
	}
}
