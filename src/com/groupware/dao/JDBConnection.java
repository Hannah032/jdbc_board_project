package com.groupware.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class JDBConnection {
	
		public Connection con;
		public Statement stmt;
		public PreparedStatement psmt;
		public ResultSet rs;
		
		public JDBConnection() {
			connect(); // 초기 연결
		}
		
		// DB 연결
		public void connect() {
			try {
				//오라클 드라이버 접속
				Class.forName("oracle.jdbc.OracleDriver");
				//DB에 연결
				String url = "jdbc:oracle:thin:@192.168.0.22:1521/orcl";
				String id = "jungha1";
				String pw = "1234";
				
				con = DriverManager.getConnection(url, id, pw);
				System.out.println("DB 연결 성공!");
			
			} catch (Exception e) {
				System.err.println("DB 연결 실패!");
				e.printStackTrace();
				con = null; // 연결 실패 시 con을 null로 설정
			}
		}
		
		// 유효한 Connection 객체를 반환 (필요 시 재연결)
		public Connection getValidConnection() throws SQLException {
		    if (con == null || con.isClosed()) {
		        System.out.println("DB 연결이 끊어졌거나 유효하지 않습니다. 재연결을 시도합니다.");
		        connect(); // 재연결 시도
		        if (con == null || con.isClosed()) {
		            throw new SQLException("DB 재연결에 실패했습니다.");
		        }
		    }
		    return con;
		}
		
		// 자원 해제
		public void close() {
			try {
				if (rs != null) rs.close();
				if (psmt != null) psmt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
}
	