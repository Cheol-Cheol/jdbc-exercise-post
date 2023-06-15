package dev.kanban.controller;

import java.util.List;

import dev.kanban.dao.KanbanDAO;
import dev.kanban.model.JoinResult;
import dev.kanban.model.Post;
import dev.kanban.view.KanbanView;

public class KanbanController {
	private KanbanDAO dao;
	private KanbanView viewer;

	public KanbanController() {
		this.dao = new KanbanDAO();
		this.viewer = new KanbanView();
	}

	public void findAll() {
		List<JoinResult> posts = dao.findAll();

		if (posts != null && posts.size() > 0)
			viewer.findAll(posts);
		else {
			Exception err = new Exception("데이터를 가져오지 못했습니다.");
			viewer.error(err);
		}
	}

	public void findById(int postId) {
		dao.findById(postId);
	}

	public void findByStatus(int statusId) {
		dao.findByStatus(statusId);
	}

	public void save(Post post) {
		dao.save(post);
	}

	public void update(int postId, Post post) {
		dao.update(postId, post);
	}

	public void deleteById(int postId) {
		dao.deleteById(postId);
	}

	public void deleteByStatusId(int statusId) {
		dao.deleteByStatusId(statusId);
	}

	public void deleteAll() {
		dao.deleteAll();
	}
}
