package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	/*
	 * 회원 가입
	 */
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 가입 확인
	 */
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if (result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
		
	}
	
	//로그인
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	//로그인 확인
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		
		String nextPage = "/admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		if(loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		}	
		else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		}
		
		return nextPage;
	}
	
	//로그아웃 확인
	@RequestMapping(value = "/logoutConfirm", method = RequestMethod.GET)
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		
		String nextPage = "redirect:/admin";
		
		session.invalidate();
		
		return nextPage;
	}
	
	//관리자목록
	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
	public String listupAdmin(Model model) {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		model.addAttribute("adminMemberVos", adminMemberVos);
		
		return nextPage;
	}
	
	//관리자 승인
	@RequestMapping(value = "/setAdminApproval", method = RequestMethod.GET)
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
	
	
	
	
	
	
	
}