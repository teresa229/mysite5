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
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	//리스트 출력
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[GalleryController]:list");
		
		List<GalleryVo> galleryList = galleryService.list();
		model.addAttribute("galleryList", galleryList);
		
		//model.addAttribute("galleryList", galleryService.list());
		
		return "/gallery/list";
	}
	
	//이미지 업로드
	@RequestMapping(value="/upload", method= {RequestMethod.GET, RequestMethod.POST})
	public String upload(@ModelAttribute GalleryVo galleryVo, @RequestParam("file") MultipartFile file, HttpSession session) {
		System.out.println("[GalleryController]:upload");
		System.out.println(galleryVo);
		
		 galleryVo.setUserNo(((UserVo)session.getAttribute("authUser")).getNo());
		
		 //session에 있는 로그인 상태 회원정보 가져오기 
//	   	 UserVo authVo = ((UserVo)session.getAttribute("authUser")); 
//		 int no = authVo.getNo();
		  
		 //galleryVo에 no담기 //session에 no변경 
//		 galleryVo.setUserNo(no);
				
		galleryService.upload(galleryVo, file);
		
		return "redirect:/gallery/list";
	}
}
