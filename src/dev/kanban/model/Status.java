package dev.kanban.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Status {
	private int id;
	private String name;
	
	// 빌더 패턴 적용
}
