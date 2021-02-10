package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping(value="/api/guestbook")
public class ApiGuestbookController {
	
	@Autowired
	private GuestService guestService;
	
	//전체 리스트 가져오기(ajax)
	@ResponseBody //데이터를 제이슨에 넣어줄 것이다.
	@RequestMapping(value="/list")
	public List<GuestVo> list() {
		System.out.println("[ApiGuestbookController]/list");
		
		return guestService.guestList();
	}
		
	//글작성
	@ResponseBody
	@RequestMapping(value="/write")
	public GuestVo write(@ModelAttribute GuestVo guestVo) { //String -> GuestVo
		System.out.println("[ApiGuestbookController]/write");
		System.out.println(guestVo.toString()); //toString()적지 않아도 찾게 되어있다.
		
		//입력된 vo값을 전달하고, 저장된 vo를 받아야 함.
		return guestService.writeResultVo(guestVo);
		
		//GuestVo vo = guestService.writeResultVo(guestVo);
		//return vo;
	}
	
	//글작성(ajax-json)
	@ResponseBody
	@RequestMapping(value="/write2")
	public GuestVo write2(@RequestBody GuestVo guestVo) { 
		System.out.println("[ApiGuestbookController]/write2");
		System.out.println(guestVo); //toString()적지 않아도 찾게 되어있다.
		
		//입력된 vo값을 전달하고, 저장된 vo를 받아야 함.
		return guestService.writeResultVo(guestVo);
		
		//GuestVo vo = guestService.writeResultVo(guestVo);
		//return vo;
	}
	
	
	
	//글삭제(ajax)
	@ResponseBody
	@RequestMapping(value="/remove" , method= {RequestMethod.GET, RequestMethod.POST})
	public int remove(@ModelAttribute GuestVo guestVo) {
		System.out.println("[ApiGuestbookController]/remove");
		System.out.println(guestVo);
	
		int count = guestService.delete(guestVo);
		System.out.println(count);
		
		return count;
	}
	
	
}


