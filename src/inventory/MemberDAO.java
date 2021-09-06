package inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import inventory_management.MemberDTO;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "bit";
	
	public MemberDAO() {
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertMember(MemberDTO dto) {
		String sql = "insert into t_member values(?,?,?,?)";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 생성
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPwd());
			pstmt.setString(4, dto.getEmail());
			
			pstmt.executeUpdate();//실행 - 개수 리턴
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public String loginMember(String id, String pwd){
		String name = null;
		String sql = "select * from t_member where member_id=? and member_pwd=?";
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);//생성
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();//실행
			
			if (rs.next())
				name = rs.getString("member_name");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return name;
	}
	
	//중복아이디를 체크하는 부분
	public String idCheck(String id) {
		String sql = "select * from t_member where member_id=?";
		getConnection();
		String idcheck = null;
		
		try {
			pstmt = conn.prepareStatement(sql);//생성
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();//실행
			
			if(rs.next()) idcheck = rs.getString("member_id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return idcheck;
	}
}