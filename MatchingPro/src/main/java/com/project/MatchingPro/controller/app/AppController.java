package com.project.MatchingPro.controller.app;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.MatchingPro.domain.battle.Battle;
import com.project.MatchingPro.domain.battle.BattleRepository;
import com.project.MatchingPro.domain.party.Party;
import com.project.MatchingPro.domain.party.PartyRepository;
import com.project.MatchingPro.domain.team.Team;
import com.project.MatchingPro.domain.team.TeamRepository;
import com.project.MatchingPro.domain.teamInfo.TeamInfo;
import com.project.MatchingPro.domain.user.User;
import com.project.MatchingPro.domain.user.UserRepository;
import com.project.MatchingPro.dto.app.NavDataDto;
import com.project.MatchingPro.service.TeamInfoService;
import com.project.MatchingPro.service.UserService;
import com.project.MatchingPro.service.app.AppService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping("app/")
public class AppController {
	
	private final AppService appService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;
	private final PartyRepository partyRepository;
	private final HttpSession session;
	private final TeamInfoService teamInfoService;
	private final BattleRepository battleRepository;

	//유저 상세보기
	@PostMapping("user/app/loginUser")
	public ResponseEntity<?> userDetail(){
		User user = (User)session.getAttribute("principal");
		//System.out.println(userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException(user.getId()+"는 존재하지 않습니다.")));
		return new ResponseEntity<User>(
				userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException(user.getId()+"는 존재하지 않습니다."))
				,HttpStatus.OK);
	}
	
	@PostMapping("app/imgUpload")
	public ResponseEntity<?> imgUpload(@RequestParam("uploadedfile") MultipartFile multipartFile) {
		return new ResponseEntity<String>(
				appService.imgUpload(multipartFile),
				HttpStatus.OK);
	}
	
	//팀 리스트
	@PostMapping("app/teamList")
	public List<Team> teamList(){
		return teamRepository.findAll();
	}
	
	//유저 리스트
	@PostMapping("app/userList")
	public List<User> userList(){
		return userRepository.findAll();
	}
		
	
		
	//팀 아이디 받아와서 해당 팀에 대한 파티 리스트 뿌리기
	@PostMapping("user/app/teamPartyList")
	public List<Party> teamPartyList(){
		User user = (User)session.getAttribute("principal");
		return partyRepository.mFindByTeamid(user.getTeams().getId());
	}
	
	//유저 아이디 받아와서 해당 유저에 대한 파티 리스트 뿌리기
	@PostMapping("user/app/userPartyList")
	public List<Party> partyList_user() {
		User user = (User)session.getAttribute("principal");
		return partyRepository.findUser_idAll(user.getId());
	}
	
	//팀 아이디로 배틀리스트
	@PostMapping("app/battleList/{teamid}")
	public List<Battle> teamByList(@PathVariable int teamid){
		return battleRepository.waitingFindAll(teamid);
	}
	
	//수락한 모든 배틀리스트
	@PostMapping("app/battleList")
	public List<Battle> battleList(){
		return battleRepository.acceptAFindAll();
	}
	
	//수락한 모든 내팀의 배틀리스트
	@PostMapping("app/battleList2/{teamid}")
	public List<Battle> battleList2(@PathVariable int teamid){
		return battleRepository.acceptMFindAll(teamid);
	}
}
