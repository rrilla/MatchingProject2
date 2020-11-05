package com.project.MatchingPro.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.MatchingPro.domain.battle.Battle;
import com.project.MatchingPro.domain.battle.BattleRepository;
import com.project.MatchingPro.domain.score.ScoreRepository;
import com.project.MatchingPro.domain.user.User;
import com.project.MatchingPro.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScoreService {

	private final ScoreRepository scoreRepository;
	private final BattleRepository battleRepository;
	private final UserRepository userRepository;
	
	
	
	@Transactional
	public ResponseEntity<?> scoreWin(User user, int battleid){
		Battle battleEntity=battleRepository.findById(battleid).get();
		battleEntity.getRequestTeam().getScore().setWin(user.getTeams().getScore().getWin()+1);
		battleEntity.setWinerTeam(user.getTeams());
		battleEntity.getResponseTeam().getScore().setLose(battleEntity.getResponseTeam().getScore().getLose()+1);
		//승무패 변경하고 배틀테이블 삭제
		//battleRepository.deleteById(battleid);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	@Transactional
	public void total(User user, int battleid) {
		int wintotal = (user.getTeams().getScore().getWin()+1)*3;
		int Drawtotal = user.getTeams().getScore().getDraw()*1;
		int LoseTotal=user.getTeams().getScore().getLose()*(-2);
		User userEntity = userRepository.findById(user.getId()).get();
		userEntity.getTeams().getScore().setTotal(wintotal+Drawtotal+LoseTotal);
		
		Battle battleEntity=battleRepository.findById(battleid).get();
		int wintotal2 = (battleEntity.getResponseTeam().getScore().getWin())*3;
		int Drawtotal2 =(battleEntity.getResponseTeam().getScore().getDraw())*1;
		int LoseTotal2=(battleEntity.getResponseTeam().getScore().getLose())*2;
		System.out.println((user.getTeams().getScore().getLose()+1)*2 + "zzxzxz");

		battleEntity.getResponseTeam().getScore().setTotal(wintotal2+Drawtotal2-LoseTotal2);
	}
	
	@Transactional
	public ResponseEntity<?> scoreDraw(User user, int battleid){
		Battle battleEntity=battleRepository.findById(battleid).get();
		battleEntity.getRequestTeam().getScore().setDraw(user.getTeams().getScore().getDraw()+1);
		battleEntity.getResponseTeam().getScore().setDraw(battleEntity.getResponseTeam().getScore().getDraw()+1);
		//승무패 변경하고 배틀테이블 삭제
		//battleRepository.deleteById(battleid);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	@Transactional
	public void total2(User user, int battleid) {
		int wintotal = user.getTeams().getScore().getWin()*3;
		int Drawtotal =( user.getTeams().getScore().getDraw()+1)*1;
		int LoseTotal=user.getTeams().getScore().getLose()*(-2);
		User userEntity = userRepository.findById(user.getId()).get();
		userEntity.getTeams().getScore().setTotal(wintotal+Drawtotal+LoseTotal);
		
		Battle battleEntity=battleRepository.findById(battleid).get();
		int wintotal2 = (battleEntity.getResponseTeam().getScore().getWin())*3;
		int Drawtotal2 =(battleEntity.getResponseTeam().getScore().getDraw())*1;
		int LoseTotal2=(battleEntity.getResponseTeam().getScore().getLose())*2;
		System.out.println((user.getTeams().getScore().getLose()+1)*2 + "zzxzxz");

		battleEntity.getResponseTeam().getScore().setTotal(wintotal2+Drawtotal2-LoseTotal2);
	}
	
	@Transactional
	public ResponseEntity<?> scoreLose(User user, int battleid){
		Battle battleEntity=battleRepository.findById(battleid).get();
		battleEntity.getRequestTeam().getScore().setLose(user.getTeams().getScore().getLose()+1);
		battleEntity.getResponseTeam().getScore().setWin(battleEntity.getResponseTeam().getScore().getWin()+1);
		//battleEntity.setWinerTeam(battleEntity.getResponseTeam());
		//승무패 변경하고 배틀테이블 삭제
		//battleRepository.deleteById(battleid);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	@Transactional
	public void total3(User user, int battleid) {
		int wintotal = (user.getTeams().getScore().getWin())*3;
		int Drawtotal = user.getTeams().getScore().getDraw()*1;
		int LoseTotal=(user.getTeams().getScore().getLose()+1)*(-2);
		User userEntity = userRepository.findById(user.getId()).get();
		userEntity.getTeams().getScore().setTotal(wintotal+Drawtotal+LoseTotal);
		
		Battle battleEntity=battleRepository.findById(battleid).get();
		int wintotal2 = (battleEntity.getResponseTeam().getScore().getWin())*3;
		int Drawtotal2 =(battleEntity.getResponseTeam().getScore().getDraw())*1;
		int LoseTotal2=(battleEntity.getResponseTeam().getScore().getLose())*2;
		System.out.println((user.getTeams().getScore().getLose()+1)*2 + "zzxzxz");

		battleEntity.getResponseTeam().getScore().setTotal(wintotal2+Drawtotal2-LoseTotal2);
	}
	

}
