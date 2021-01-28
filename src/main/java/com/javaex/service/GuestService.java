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
}
