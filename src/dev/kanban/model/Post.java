package dev.kanban.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
	private int id;
	private String title;
	private String description;
	private int statusId;
	
	// 빌더 패턴 적용
}
