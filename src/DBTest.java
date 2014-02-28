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
			
			// 프로시저 만들
			createProcedure(conn);
			
			// 회원가입 프로시저 호출 
			cs = conn.prepareCall("{call register(?,?)}");
            cs.setString("user_id", "hyogu");
            cs.setString("e_mail", "hook3748@naver.com");
            cs.execute();
            
            // 호출 확인 
			sql = "select * from user";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String user_id = rs.getString("user_id");
				String e_mail = rs.getString("e_mail");
				System.out.printf("%s : %s \n", user_id, e_mail);
			}
			
			// 카드 만들기 프로시저 호출 
			cs = conn.prepareCall("{call pick_character(?)}");
            cs.setString("user_id", "hyogu");
            cs.execute();
            
            // 호출 확인 
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
		
		// register procedure
		sql = "CREATE PROCEDURE register(IN user_id VARCHAR(10), ";
		sql += "IN e_mail VARCHAR(45)) ";
		sql += "BEGIN ";
		sql += "INSERT INTO user (user_id, e_mail) VALUES (user_id, e_mail); ";
		sql += "END";
		
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Create Procedure Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		// pick_character procedure
		sql = "CREATE PROCEDURE pick_character(IN user_id VARCHAR(10)) ";
		sql += "BEGIN ";
		sql += "DECLARE grade INT; ";
		sql += "SET grade = FLOOR(1 + RAND() * 5); ";
		sql += "INSERT INTO `character` (user_id, grade, hp, damage, img, expr) ";
		sql += "VALUES (user_id, grade, 100 * grade, 10 * grade, 'img', 100*grade); ";
		sql += "END";
		
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Create Procedure Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
