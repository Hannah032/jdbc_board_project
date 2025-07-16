package 게시판.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnection {

    private static final String URL = "jdbc:oracle:thin:@192.168.0.22:1521/orcl";
    private static final String ID = "jungha1";
    private static final String PW = "1234";

    public Connection con;
    public Statement stmt;
    public PreparedStatement psmt;
    public ResultSet rs;

    public JDBConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(URL, ID, PW);
            System.out.println("DB 연결 성공");
        } catch (Exception e) {
            System.err.println("DB 연결 실패");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (psmt != null) psmt.close();
            // if (con != null) con.close();  <-- 이 부분을 주석 처리하여 연결을 닫지 않도록 합니다.
            System.out.println("DB 리소스 해제"); // 메시지를 변경하여 연결이 닫히지 않음을 명확히 합니다.
        } catch (SQLException e) {
            System.err.println("DB 리소스 해제 중 오류 발생");
            e.printStackTrace();
        }
    }
}