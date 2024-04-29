package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ghkdlxld {

	public static void main(String[] args) {
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 점수 =====");
		System.out.print("ID 입력 : ");
		String id = sc.next();
		System.out.print("PW 입력 : ");
		String pw = sc.next();
		String vip = "";
		int num = 50;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost/hangman";
			String user = "root";
			String password = "12345";

			conn = DriverManager.getConnection(url, user, password);

			String sql = "select id,score from hangman.user where id = ? and pw = ?  ";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();

			if (rs.next()) {
				int score = rs.getInt("score");

				String sql2 = "update hangman.user set score = ? where id = ? and pw = ?";

				psmt = conn.prepareStatement(sql2);
				psmt.setInt(1, score + num);
				psmt.setString(2, id);
				psmt.setString(3, pw);

				int row = psmt.executeUpdate();

				if (row > 0) {
					System.out.println("점수 갱신");

				} else {
					System.out.println("다시.");
				}
				System.out.println(score + num);
			}
			String sql1 = "select score from hangman.user where id=? ";

			psmt = conn.prepareStatement(sql1);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			if (rs.next()) {
				int score = rs.getInt("score");
				if (score < 100) {
					vip = "Bronze";
				} else if (score >= 100 && score < 200) {
					vip = "Silver";
				} else if (score >= 200 && score < 300) {
					vip = "Gold";
				} else if (score >= 300) {
					vip = "G.O.A.T";
				}

				String sql2 = "update hangman.user set vip = ? where id = ? and pw = ?";

				psmt = conn.prepareStatement(sql2);
				psmt.setString(1, vip);
				psmt.setString(2, id);
				psmt.setString(3, pw);

				int row = psmt.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
