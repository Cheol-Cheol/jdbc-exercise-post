package dev.kanban.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinResult {
	private int id;
	private String title;
	private String description;
	private int statusId;
	private String name;
	
	@Override
	public String toString() {
		return "JoinResult [id=" + id + ", title=" + title + ", description=" + description + ", statusId=" + statusId
				+ ", name=" + name + "]";
	}
}
