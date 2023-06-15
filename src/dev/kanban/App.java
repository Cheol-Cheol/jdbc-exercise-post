package dev.kanban;

import dev.kanban.controller.KanbanController;
import dev.kanban.model.Post;

public class App {

	public static void main(String[] args) {
		KanbanController controller = new KanbanController();

		// controller.findAll();

		// controller.findById(1);

		// controller.findByStatus(1);

		// Post newPost = Post.builder().title("새로운 post 제목 추가").description("새로운 post
		// 추가").statusId(1).build();
		// controller.save(newPost);

		// Post updatePost = Post.builder().title("수정된 post").build();
		// controller.update(4, updatePost);

		// controller.deleteById(0);

		// controller.deleteByStatusId(1);

		// controller.deleteAll();
	}

}
