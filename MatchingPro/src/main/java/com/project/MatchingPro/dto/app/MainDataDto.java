package com.project.MatchingPro.dto.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainDataDto {
	private String username;
	private String nickname;
	private String phone;
	private String image;
	
	private String t_name;
	private String t_image;
	private String t_explaintation;
}