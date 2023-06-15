package dev.kanban.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.kanban.model.JoinResult;
import dev.kanban.model.Post;
import dev.kanban.util.DBUtil;
import dev.kanban.view.KanbanView;

public class KanbanDAO {
	private KanbanView view;

	public KanbanDAO() {
		this.view = new KanbanView();
	}

	// 조인하고 전체 조회
	public List<JoinResult> findAll() {
		ResultSet rs = null;
		// sql 쿼리 작성
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						" SELECT p.id, title, description, p.status_id, name FROM post p JOIN status s ON p.status_id = s.id");) {

			rs = stmt.executeQuery();
			List<JoinResult> posts = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				int statusId = rs.getInt("status_id");
				String name = rs.getString("name");

				// 객체 생성하고 -> 리스트에 널고
				JoinResult item = JoinResult.builder().id(id).title(title).description(description).statusId(statusId)
						.name(name).build();
				posts.add(item);
			}
			
			
			return posts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 조인하고 아이디로 조회
	public void findById(int postId) {
		ResultSet rs = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						" SELECT p.id, title, description, p.status_id, name FROM post p JOIN status s ON p.status_id = s.id where p.id = "
								+ postId);) {

			rs = stmt.executeQuery();
			JoinResult post = null;

			while (rs.next()) {
				String title = rs.getString("title");
				String description = rs.getString("description");
				int statusId = rs.getInt("status_id");
				String name = rs.getString("name");

				JoinResult item = JoinResult.builder().id(postId).title(title).description(description)
						.statusId(statusId).name(name).build();
				post = item;
			}

			view.findById(post);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 조인하고 상태값으로 조회
	public void findByStatus(int status) {
		ResultSet rs = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						" SELECT title, description, name FROM post p JOIN status s ON p.status_id = s.id where p.status_id = "
								+ status);) {

			rs = stmt.executeQuery();
			List<JoinResult> posts = new ArrayList<>();

			while (rs.next()) {
				String title = rs.getString("title");
				String description = rs.getString("description");
				String name = rs.getString("name");

				JoinResult item = JoinResult.builder().title(title).description(description).name(name).build();
				posts.add(item);
			}

			view.findByStatus(posts);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 저장
	public void save(Post post) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO post(title, description, status_id) VALUES(?,?,?)");) {

			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getDescription());
			stmt.setInt(3, post.getStatusId());

			stmt.execute();

			view.success();
		} catch (SQLException e) {
			e.printStackTrace();
			view.error(e);
		}
	}

	// post 수정
	public void update(int postId, Post post) {
		String sql = "UPDATE post SET id = " + postId;
		if (post.getTitle() != null)
			sql += (", title = \"" + post.getTitle() + "\"");
		if (post.getDescription() != null)
			sql += (", description = \"" + post.getDescription() + "\"");
		if (post.getStatusId() != 0)
			sql += (", status_id = " + post.getStatusId());
		sql += ("WHERE id = " + postId);

		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.executeUpdate();
			view.success();
		} catch (SQLException e) {
			e.printStackTrace();
			view.error(e);
		}
	}

	// id 기준으로 삭제
	public void deleteById(int postId) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE id = ?");) {
			stmt.setInt(1, postId);
			stmt.execute();
			view.success();
		} catch (SQLException e) {
			e.printStackTrace();
			view.error(e);
		}
	}

	// status 기준으로 삭제
	public void deleteByStatusId(int statusId) {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE status_id = ?");) {
			stmt.setInt(1, statusId);
			stmt.execute();
			view.success();
		} catch (SQLException e) {
			e.printStackTrace();
			view.error(e);
		}
	}

	// 전체 삭제
	public void deleteAll() {
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement("TRUNCATE table post");) {

			stmt.execute();
			view.success();
		} catch (SQLException e) {
			e.printStackTrace();
			view.error(e);
		}
	}
}
