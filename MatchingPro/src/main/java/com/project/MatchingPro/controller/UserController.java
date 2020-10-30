package com.project.MatchingPro.controller;


import java.util.List;

import javax.servlet.http.HttpSession;	


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;


import com.project.MatchingPro.domain.user.User;
import com.project.MatchingPro.domain.user.UserRepository;
import com.project.MatchingPro.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {	//app,web 둘다적용


	private final UserService userService;
	private final HttpSession session;
	private final UserRepository userRepository;
	 
	//회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody User user) {
		return userService.join(user);
	}
	
	//id 중복체크

	@GetMapping("/idCheck/{loginid}")
	public ResponseEntity<?> idCheck(@PathVariable String loginid){
		return userService.idCheck(loginid);
	}
	
	//유저 상세보기
	@GetMapping("/userDetail/{userid}")
	public ResponseEntity<?> detail(@PathVariable int userid){
		return userService.detail(userid);
	}
	
	//닉네임 중복체크

	@GetMapping("/nicknameCheck/{nickname}")
	public ResponseEntity<?> nicknameCheck(@PathVariable String nickname){
		return userService.nicknameCheck(nickname);
	}
	
	//로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		session.removeAttribute("principal");
		//session.invalidate();	//모든 세션 정보 삭제
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/userList")
	public List<User> list(){
		return userRepository.findAll();
	}
}
