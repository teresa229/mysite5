package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
		
	//리스트
	public List<BoardVo> list() {
		System.out.println("[service]:selectList");
		
		List<BoardVo> boardList = boardDao.selectList();
		
		//return boardDao.selectList();
		return boardList;
	}
	
	//리스트(리스트 + 검색)
	public List<BoardVo> getBoardList2(String keyword){
		
		System.out.println("[service]:getBoardList2");
		//System.out.println("keyword=" + keyword);
		
		List<BoardVo> boardList = boardDao.selectList2(keyword);
		
		return boardList; //null ->boardList : test하면서 바뀐다.
	}
	
	//리스트(리스트 + 검색 + 페이징)
	public Map<String, Object> getBoardList3(String keyword, int crtPage){ //List<BoardVo> -->Map<String, Object>
		
		System.out.println("[service]:getBoardList3");
		
		//crtPage -> 시작번호, 끝번호를 불러와야 한다. rownum 1--> 1,10 2-->11,20 3-->21,30
		////////////////////////////////////
		//리스트 구하기
		////////////////////////////////////

		//페이지당 글 갯수
		int listCnt = 10;
		
		//현재 페이지
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);  //사망연산자
		
		
		/*
		 * if(crtPage>0) { crtPage = crtPage; }else { crtPage = 1; }
		 */
		
		//시작글 번호 startRNum            1-->crtPage, 2-->
		int startRNum = (crtPage-1) * listCnt + 1;     //0*10 -> 0+1, 1*10->10+1,  2*10->20+1, 3*10->30+1
		
		
		//끝번호 endRNum
		int endRNum = (startRNum + listCnt) -1;
		
		List<BoardVo> boardList = boardDao.selectList3(keyword,startRNum,endRNum);
		
		
		////////////////////////////////////
		//페이징 계산
		////////////////////////////////////
		
		//페이지당 버튼 개수
		int pageBtnCount = 5;
		
		//전제 글갯수 구하기
		int totalCount = boardDao.selectTotalCnt(keyword); //(키워드를 넣어주어야 한다.)
		
		//1 --> 1~5
		//2 --> 1~5
		//3 --> 1~5
		//4 --> 1~5
		//5 --> 1~5
		//6 --> 6~10
		//7 --> 6~10
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount) * pageBtnCount;  
		/*
		 * 1/5.0-->0.2 올림--> 1.0 -->정수 1 --> 1*5 --> 5
		 * 2/5.0-->0.4 올림--> 1.0 -->      --> 1*5 --> 5
		 * 5/5.0-->1.0 올림--> 1.0 --> --> 1*5 --> 5
		 * 6/5.0-->1.2 올림--> 2.0 --> 정수 2 --> 2*5 --> 10 
		 * 10/5.0->2.0 올림--> 2.0 --> 정수 2 --> 2*5 --> 10
		 * 11/5.0->2.2 올림--> 3.0 --> 정수 3 --> 3*5 --> 15
		 */
		
		//시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount-1);
		
		/*
		int endPageBtnNo = 1*5;                   // (1/5)*5 --> (0)*5 -->0  //올림 처리하게 만드는 공식
		int endPageBtnNo = 2*5;                   // (2/5)*5 --> (0)*5 -->0
		int endPageBtnNo = 3*5;                   // (3/5)*5 --> (0)*5 -->0
		*/
		
		//다음버튼 --boolean형
		boolean next;
		
		if(endPageBtnNo * listCnt < totalCount) { //5*10 < 51  다음 버튼을 보여주어야 하는 상황.
			next = true;
		}else {                                   //5*9 < 45
			next = false;
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);  //1234/10 ---124까지 나오뎄구나.
		}
		
		//이전버튼 --boolean형
		boolean prev;
		if(startPageBtnNo !=1) {
			prev = true;
		}else {
			prev = false;
		}
		
		//boardList, prev, startPageBtnNo, endPageBtnNo, next --> jsp에 전달(map으로 전달+ list까지 묶어서 보내기)
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("prev", prev);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("next", next);
		
		
		return pMap; //null ->boardList-> pMap : test하면서 바뀐다.
	}
	
	//등록
	public int write(BoardVo boardVo) {
		System.out.println("[service]:write");
		
		for(int i=1; i<=1234; i++) {
			boardVo.setTitle(i+"번째 글 제목입니다.");
			boardVo.setContent(i+"번째 글 내용입니다.");
			boardDao.insert(boardVo);
		}
		
		return boardDao.insert(boardVo);
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
		
		boardDao.updateHit(no);
		System.out.println(boardDao.toString());
		
		BoardVo bVo = boardDao.selectOne(no);
		System.out.println("[service]:read"+bVo.toString());
	
		return bVo;
	}
}
