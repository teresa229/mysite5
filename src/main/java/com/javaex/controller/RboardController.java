package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.RboardService;
import com.javaex.vo.RboardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/rboard")
public class RboardController {
	
	@Autowired
	private RboardService rboardService;
	
	//댓글게시판 리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("rboard[controller]:list");
		
		List<RboardVo> boardList = rboardService.list();
		
		model.addAttribute("boardList", boardList);
		
		return "rboard/list";
	}
	
	//댓글게시판 글쓰기
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute RboardVo boardVo, HttpSession session) {
		System.out.println("rboard[controller]:write");
		
		//세션에서 사용자 정보 가져오기
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		//Vo에 no담기
		int no = authVo.getNo();
		boardVo.setUserNo(no);   //setUserNo
			
		rboardService.write(boardVo);

		return "redirect:/rboard/list";
	}
	
	//댓글게시판 글쓰기폼
	@RequestMapping(value="/writeForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("rboard[controller]:writeForm");
		
		return "rboard/writeForm";
	}
	
	//댓글쓰기폼
	@RequestMapping(value="/commentForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String commentForm(@RequestParam("groupNo")int groupNo, Model model) {
		System.out.println("rboard[controller]:commentForm");
		
		RboardVo boardVo = rboardService.read(groupNo);
		System.out.println(boardVo.toString());
		
		model.addAttribute("rVo", boardVo);
		
		return "rboard/writeForm";
	}
	
	//삭제
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public String remove(@RequestParam("no") int no) {
		System.out.println("rboard[controller]:remove");
		
		rboardService.remove(no);
		
		return "redirect:/rboard/list";
	}
	
	//댓글게시판 수정
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute RboardVo boardVo) {
		System.out.println("rboard[controller]:modify");
		rboardService.modify(boardVo);
		
		return "redirect:/rboard/list";
	}
	
	//댓글게시판 수정폼
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no")int no, Model model) {
		System.out.println("rboard[controller]:modifyForm");
		
		RboardVo boardVo = rboardService.modifyForm(no);
		System.out.println("rboard[controller]:modifyForm"+boardVo.toString());
		model.addAttribute("boardVo", boardVo);
		
		return "rboard/modifyForm";
	}
	
	//댓글 읽기
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no")int no, Model model) {
		System.out.println("rboard[controller]:read");
		
		RboardVo boardVo = rboardService.read(no);
		model.addAttribute("bVo", boardVo);
		
		return "rboard/read";
	}
}
