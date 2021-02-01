package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;

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
	
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute BoardVo boardVo) {
		System.out.println("[controller]:write");
		
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
