package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.RboardDao;
import com.javaex.vo.RboardVo;


@Service
public class RboardService {

	@Autowired
	private RboardDao rboardDao;
		
	//리스트
	public List<RboardVo> list() {
		System.out.println("rboard[service]:selectList");
		
		List<RboardVo> boardList = rboardDao.selectList();
		
		return boardList;
	}
	
	//등록
	public void write(RboardVo boardVo) {
		System.out.println("rboard[service]:write");
		
		if(boardVo.getGroupNo()> 0) {
			
			boardVo.setOderNo(boardVo.getOrderNo()+1);
			System.out.println("rboard[service]:write-boardVo.getOrderNo()"+boardVo.getOrderNo());
			boardVo.setDepth(boardVo.getDepth()+1);
			System.out.println("rboard[service]:write-boardVo.getDepth()"+boardVo.getDepth());
			
			rboardDao.orderNoUpdate(boardVo); 

			rboardDao.commentInsert(boardVo);
			System.out.println("rboard[service]:write-commentInsert"+boardVo.toString());
			
		} else {
			rboardDao.insert(boardVo);
		}
	}
	
	//삭제
	public int remove(int no) {
		System.out.println("rboard[service]:remove");
		return rboardDao.delete(no);
	}
	
	//수정
	public int modify(RboardVo rboardVo) {
		System.out.println("rboard[service]:modify");
		
		return rboardDao.update(rboardVo);
	}
	
	//수정폼
	public RboardVo modifyForm(int no) {
		System.out.println("rboard[service]:modifyForm");
		
		return rboardDao.selectOne(no);
	}
	
	//글보기
	public RboardVo read(int no) {
		System.out.println("rboard[service]:read");
		
		int count = rboardDao.updateHit(no);
		System.out.println("rboard[service]:read/updateHit"+ count);
		
		RboardVo bVo = rboardDao.selectOne(no);
		System.out.println("rboard[service]:read/selectOne"+bVo.toString());
	
		return bVo;
	}
	

}
