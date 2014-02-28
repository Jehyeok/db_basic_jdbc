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
			
//			sql = "SELECT * FROM `character`";
//			rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				String user_id = rs.getString("user_id");
//				System.out.printf("%s\n", user_id);
//			}
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("DB Error: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
