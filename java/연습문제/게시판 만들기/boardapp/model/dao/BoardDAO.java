package boardapp.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import boardapp.db.DBUtil;
import boardapp.model.dto.Board;

public class BoardDAO {

    // 게시글 전체 조회
    public List<Board> getAllBoards() {
        List<Board> list = new ArrayList<>();
        String sql = "SELECT * FROM board ORDER BY id DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Board b = new Board(
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("writer")
                );
                b.setId(rs.getInt("id"));
                b.setDate(rs.getTimestamp("date").toString());
                b.setViews(rs.getInt("views"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 게시글 추가
    public void insertBoard(Board board) {
        String sql = "INSERT INTO board (title, content, writer) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 게시글 삭제
    public void deleteBoard(int id) {
        String sql = "DELETE FROM board WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 게시글 수정
    public void updateBoard(Board board) {
        String sql = "UPDATE board SET title=?, content=?, writer=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setString(3, board.getWriter());
            pstmt.setInt(4, board.getId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 조회수 증가
    public void increaseViews(int id) {
        String sql = "UPDATE board SET views = views + 1 WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
