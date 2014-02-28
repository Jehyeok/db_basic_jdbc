import java.sql.*;

public class DBTest {
	public static void main(String[] args) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String sql;
		CallableStatement cs;
		
		String addr="jdbc:mysql://localhost/mydb";
		String user="hyogu";
		String pw="123456";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver Error: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Driver Loading Success");
		
		try {
			conn = DriverManager.getConnection(addr, user, pw);
			System.out.println("Connect Success");
			stmt = conn.createStatement();
			
//			createProcedure(conn);
			
			sql = "DROP PROCEDURE IF EXISTS register ";
			sql += "DELIMITER $$ ";
			sql += "CREATE PROCEDURE register(";
			sql += "IN user_id VARCHAR(10), ";
			sql += "IN e_mail VARCHAR(45)) ";
			sql += "BEGIN ";
			sql += "INSERT INTO `user` (user_id, e_mail) VALUES (user_id, e_mail) ";
			sql += "END $$ ";
			sql += "DELIMITER ;";
			
			try {
				stmt = conn.createStatement();
				stmt.execute(sql);
			} catch (SQLException e) {
				System.out.println("Create Procedure Error: " + e.getMessage());
				e.printStackTrace();
			}
			
			cs = conn.prepareCall("{call register(?,?)}");
            cs.setString("user_id", "hyogu");
            cs.setString("e_mail", "hook3748@naver.com");
            cs.execute();
            
			sql = "select * from user";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String user_id = rs.getString("user_id");
				String e_mail = rs.getString("e_mail");
				System.out.printf("%s : %s \n", user_id, e_mail);
			}
			
			cs = conn.prepareCall("{call pick_character(?)}");
            cs.setString("user_id", "hyogu");
            cs.execute();
            
			sql = "SELECT * FROM `character`";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String user_id = rs.getString("user_id");
				System.out.printf("%s\n", user_id);
			}
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("DB Error: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private static void createProcedure(Connection conn) {
		Statement stmt;
		String sql;
		ResultSet rs;
		
		sql = "DROP PROCEDURE IF EXISTS register ";
		sql += "DELIMITER $$ ";
		sql += "CREATE PROCEDURE register(IN user_id VARCHAR(10), IN e_mail VARCHAR(45)) ";
		sql += "BEGIN ";
		sql += "INSERT INTO `user` (user_id, e_mail) VALUES (user_id, e_mail) ";
		sql += "END $$ ";
		sql += "DELIMITER ;";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
