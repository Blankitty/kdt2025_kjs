package com.example.db;

import exam.dao.StudentDAO;
import exam.vo.StudentVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBExam {

    public static Connection makeConnection() {
        String url = "jdbc:mysql://localhost:3306/school_db?serverTimezone=Asia/Seoul";
        String userName = "root";
        String userPass = "1234";
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("데이터베이스 연결중 ...");
            con = DriverManager.getConnection(url, userName, userPass);
            System.out.println("데이터베이스 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾지 못했습니다...");
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패");
            System.out.println(e.getMessage());
        }

        return con;
    }

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // 1. 오짱구 전화번호 수정
        boolean updated = dao.updatePhone(101, "010-6666-7878");
        System.out.println("오짱구 전화번호 수정 " + (updated ? "성공" : "실패"));

        // 2. 유재석, 나검사 추가
        dao.insert(new StudentVO(200, "유재석", "010-3626-1111", "rhu@gmail.com"));
        dao.insert(new StudentVO(300, "나검사", "010-8888-9999", "naking@naver.com"));

        // 3. 이순신 삭제
        boolean deleted = dao.delete(102);
        System.out.println("이순신 삭제 " + (deleted ? "성공" : "실패"));

        // 4. 전화번호로 학생 찾기
        StudentVO result = dao.findByPhone("010-3626-1111");
        System.out.println("전화번호 010-3626-1111 조회 결과:");
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("학생을 찾을 수 없습니다.");
        }
    }
}
