package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[controller]:list");
		
		List<BoardVo> boardList = boardService.list();
		
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	//리스트 (리스트+검색 기능)
	@RequestMapping(value="/list2", method= {RequestMethod.GET, RequestMethod.POST})
	public String list2(@RequestParam(value="keyword", required= false, defaultValue="") String keyword, Model model) { //파라미터에 항상 값을 주기로 되어있다. 있을수도 있고 없을수도 있는 상황에 사용하기 위해..ppt06/
		System.out.println("[controller]:list2");
		//System.out.println("keyword=" + keyword);
		
		List<BoardVo> boardList = boardService.getBoardList2(keyword);
		model.addAttribute("boardList",boardList);
		
		return "board/list2";
	}
	
	//리스트 (리스트+검색 기능)
	@RequestMapping(value="/list3", method= {RequestMethod.GET, RequestMethod.POST})
	public String list3(@RequestParam(value="keyword", required= false, defaultValue="") String keyword, 
						@RequestParam(value="crtPage", required= false, defaultValue="1") int crtPage,  //defaultValue= [문자열]로 처리해야 한다. String->int
						Model model) { //파라미터에 항상 값을 주기로 되어있다. 있을수도 있고 없을수도 있는 상황에 사용하기 위해..ppt06/
		System.out.println("[controller]:list3");
		//System.out.println("keyword=" + keyword);
		//System.out.println("crtPage=" + crtPage);
		
		Map<String, Object> pMap = boardService.getBoardList3(keyword,crtPage);
		System.out.println(pMap);
		
		model.addAttribute("pMap", pMap);
		
		return "board/list3";
	}
	
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[controller]:write");
		
		//세션에서 사용자 정보 가져오기
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		//Vo에 no담기
		int no = authVo.getNo();
		boardVo.setUserNo(no);   //setUserNo
			
		int count = boardService.write(boardVo);

		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/writeForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("[controller]:writeForm");
		
		return "board/writeForm";
	}
	
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public String remove(@RequestParam("no") int no) {
		System.out.println("[controller]:remove");
		
		boardService.remove(no);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute BoardVo boardVo) {
		System.out.println("[controller]:modify");
		System.out.println("[controller]:modify"+boardVo.toString());
		boardService.modify(boardVo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no")int no, Model model) {
		System.out.println("[controller]:modifyForm");
		
		BoardVo boardVo = boardService.modifyForm(no);
		System.out.println("[controller]:modifyForm"+boardVo.toString());
		model.addAttribute("boardVo", boardVo);
		
		return "board/modifyForm";
	}
	
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no")int no, Model model) {
		System.out.println("[controller]:read");
		
		BoardVo boardVo = boardService.read(no);
		model.addAttribute("bVo", boardVo);
		
		return "board/read";
	}
}
