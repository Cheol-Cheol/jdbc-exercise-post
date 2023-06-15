package dev.kanban.view;

import java.util.List;

import dev.kanban.model.JoinResult;

public class KanbanView {
	public void findAll(List<JoinResult> joinResults) {
		for (JoinResult joinResult : joinResults) {
			System.out.println(joinResult);
		}
	}

	public void findById(JoinResult joinResult) {
		System.out.println(joinResult);
	}

	public void findByStatus(List<JoinResult> joinResults) {
		for (JoinResult joinResult : joinResults) {
			System.out.println(joinResult);
		}
	}

	public void success() {
		System.out.println("¼º°ø!");
	}

	public void error(Exception e) {
		System.out.println(e.getMessage());
	}
}
