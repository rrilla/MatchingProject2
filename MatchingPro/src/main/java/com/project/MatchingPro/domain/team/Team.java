package com.project.MatchingPro.domain.team;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.MatchingPro.domain.party.Party;
import com.project.MatchingPro.domain.user.User;

import lombok.Data;

@Entity
@Data
public class Team {

	@Id // 기본키 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 데이터베이스 번호증가 전략을 따라가기
	private int id;

	@Column(unique = true, nullable = false)
	private String name;


	@Column(nullable = false)
	private String explaintation; // 팀설명
	
	@Column(length = 1000000000)
	private String image;
	
	@OneToOne
	private User owner;

	@JsonIgnoreProperties({"teams"})
	@OneToMany(mappedBy = "teams", fetch = FetchType.LAZY)
	private List<User> users;
	
	@JsonIgnoreProperties({"team","user"})
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<Party> partys;
	
}
