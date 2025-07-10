package exam.dao;

import exam.vo.StudentVO;
import com.example.db.DBExam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. 전체 조회
    public List<StudentVO> findAll() {
        List<StudentVO> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = DBExam.makeConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new StudentVO(
                    rs.getInt("stdno"),
                    rs.getString("stdname"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. 학생 추가
    public boolean insert(StudentVO s) {
        String sql = "INSERT INTO student (stdno, stdname, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBExam.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, s.getStdno());
            pstmt.setString(2, s.getStdname());
            pstmt.setString(3, s.getPhone());
            pstmt.setString(4, s.getEmail());

            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. 전화번호 수정
    public boolean updatePhone(int stdno, String newPhone) {
        String sql = "UPDATE student SET phone = ? WHERE stdno = ?";
        try (Connection conn = DBExam.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPhone);
            pstmt.setInt(2, stdno);

            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. 학생 삭제
    public boolean delete(int stdno) {
        String sql = "DELETE FROM student WHERE stdno = ?";
        try (Connection conn = DBExam.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stdno);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. 전화번호로 검색
    public StudentVO findByPhone(String phone) {
        String sql = "SELECT * FROM student WHERE phone = ?";
        try (Connection conn = DBExam.makeConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new StudentVO(
                    rs.getInt("stdno"),
                    rs.getString("stdname"),
                    rs.getString("phone"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
