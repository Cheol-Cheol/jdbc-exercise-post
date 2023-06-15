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

	// �����ϰ� ��ü ��ȸ
	public List<JoinResult> findAll() {
		ResultSet rs = null;
		// sql ���� �ۼ�
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

				// ��ü �����ϰ� -> ����Ʈ�� �ΰ�
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

	// �����ϰ� ���̵�� ��ȸ
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

	// �����ϰ� ���°����� ��ȸ
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

	// ����
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

	// post ����
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

	// id �������� ����
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

	// status �������� ����
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

	// ��ü ����
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
