package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/guestbook")
public class GuestbookController {
	
	//필드
	@Autowired
	private GuestService guestService;
	
	//리스트
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("/guestbook/list");
		
		List<GuestVo> guestbookList = guestService.guestList();
		model.addAttribute("gList", guestbookList);
		
		return "/guestbook/addList";
	}
	
	//등록
	@RequestMapping(value="/insert", method= {RequestMethod.GET, RequestMethod.POST})
	public String insert(@ModelAttribute GuestVo guestVo) {
		System.out.println("/guestbook/insert");
		
		guestService.insert(guestVo);
		
		return "redirect:/guestbook/list";
	}
	
	//삭제폼
	@RequestMapping(value="/deleteForm", method= {RequestMethod.GET,RequestMethod.POST})
	public String deleteForm() {
		System.out.println("/guestbook/deleteForm");
		
		return"/guestbook/deleteForm";
	}
	
	//삭제
	@RequestMapping(value="/delete", method= {RequestMethod.GET,RequestMethod.POST})
	public String delete(@ModelAttribute GuestVo guestVo) {
		System.out.println("/guestbook/delete");
		
		int count = guestService.delete(guestVo);
		if(count==1) { //성공
			return "redirect:/guestbook/list";
		}else {        //실패
			return "redirect:/guestbook/deleteForm?result=fail&no="+guestVo.getNo();
		}
		
	}
}
