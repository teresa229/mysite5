package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.UserDao;
import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/user")
public class UserController {

	//필드 - 메모리에 올려서 자동으로 올려줘. doa는 영역 밖이다. 세팅은 appli
	@Autowired
	private UserDao userDao; //기존 코드 망가트리지 않으려고 남겨둠
	
	@Autowired
	private UserService userService;
	//생성자
	//g/s생략
	/*일반메소드*/
	
	//회원가입 폼
	@RequestMapping(value="/joinForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		System.out.println("/user/joinForm");
		
		return "user/joinForm";	
	}
	
	//회원가입 
	@RequestMapping(value="/join", method= {RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("/user/join");
		System.out.println(userVo.toString());
		
		int count = userService.join(userVo);  //+control
		//userDao.insert(userVo);
		
		return "user/joinOk";
	}
	
	//로그인 폼
	@RequestMapping(value="/loginForm", method={RequestMethod.GET, RequestMethod.POST})
	public String loginForm(){
		System.out.println("/user/loginForm");
		
		return "user/loginForm";
	}
	
	//로그인
	@RequestMapping(value="/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {  //UserVo userVo로 생략도 가능하다.
		System.out.println("/user/login");
		System.out.println(userVo.toString());
		
		UserVo authUser = userService.login(userVo);                           //session에 넣을 이름으로 authUser
		//UserVo authUser = userDao.selectUser(userVo);
		
		//성공했을 때
		if(authUser != null) {
			System.out.println("로그인 성공");		
			session.setAttribute("authUser", authUser);
			return "redirect:/"; 
			
		} else {
			System.out.println("로그인 실패");	
			return "redirect:/user/loginForm?result=fail";   
		}

	}	
	
	//로그아웃
	@RequestMapping(value="logout", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("/user/logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	//수정폼
	@RequestMapping(value="/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("/user/modifyForm");
		/*
		 * //세션영역에 있는 로그인 상태인 유저 no 가져오기 
		 * step 1: 세션의 authUser 가져와 -> userVo에 담기 - object에서 vo로 형변환 
		 * UserVo authVo = (UserVo)session.getAttribute("authUser");
		 * 
		 * step 2: getNo()로 no값을 가져온다. 
		 * int no = authVo.getNo();
		 * System.out.println("no넣은 후"+authVo.toString()); //no넣은 후UserVo [no=23, id=null, password=null, name=김경아, gender=null]
		 * //UserVo userVo = userDao.selectOne(no);
		 */		
		
		//세션에서 no값 가져오기
		int no = ((UserVo)session.getAttribute("authUser")).getNo();
		
		//세션값이 없으면 -> 로그인 폼으로 보내버리는 장치!
		
		//회원정보 가져오기
		UserVo userVo = userService.modifyForm(no);
		
		//jsp에 데이터 보내기
		model.addAttribute("uvo", userVo);
		System.out.println(userVo.toString());               //UserVo [no=23, id=eee, password=1234, name=김경아, gender=female]
		
		return "user/modifyForm";
	}
	
	//수정
	//http://localhost:8088/mysite5/user/modify?password=1234&name=김&gender=female
	@RequestMapping(value="/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("/user/modify");
		System.out.println(userVo.toString());          //UserVo [no=0, id=null, password=1234, name=김경아 마지막, gender=female]
		
		//세션에 사용자 정보 가져오기
		UserVo authVo = (UserVo)session.getAttribute("authUser");
		
		/* 
		 * step 1: 세션에서 no값 가져오기
		 * int no = authVo.getNo();
		 * 
		 * step 2: vo애 정보 추가
		 * userVo.setNO(no);
		 */
		
		//userVo에 no담기
		userVo.setNo(authVo.getNo());
		System.out.println("no+"+userVo.toString());    //no+UserVo [no=23, id=null, password=1234, name=김경아 마지막, gender=female]
		
		//회원정보
		int count = userService.modify(userVo);
		System.out.println("xml+"+userVo.toString());   //xml+UserVo [no=23, id=null, password=1234, name=김경아 마지막, gender=female]
		
		//세션에 이름변경 : 이름이 안 바뀌어 --> session에 넣어줌 ****************************
		authVo.setName(userVo.getName());
		
		return "redirect:/";
	}
}
