package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CustomerDao;
import model.vo.CustomerVO;

public class CustomerDaoImpl implements CustomerDao{

	final static String DRIVER    ="oracle.jdbc.driver.OracleDriver"; 
	final static String URL    = "jdbc:oracle:thin:@192.168.0.40:1521:xe";
	final static String USER    = "yu";
	final static String PASS    = "1234";

	
	public CustomerDaoImpl() throws Exception{
	 	// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버 로딩 성공");
		
	}
	
	public void insertCustomer(CustomerVO vo) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement pr = null;
		
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO MEMBER(tel,name,addr,email,addtel) VALUES(?,?,?,?,?)";
		
		// 4. sql 전송객체 (PreparedStatement)		
		
		pr = con.prepareStatement(sql);
		// 5. sql 전송
		pr.setString(1, vo.getTel());
		pr.setString(2, vo.getName());
		pr.setString(3, vo.getAddr());
		pr.setString(4, vo.getEmail());
		pr.setString(5, vo.getAddtel());
		
		pr.executeUpdate();
		
		}finally {
		// 6. 닫기 
			pr.close();
			con.close();
			
			
		}
		

	}
	
	/*
	 * 메소드명 : selectbytel
	 * 인자 : 검색할 전화번호
	 * 리턴값 :전화번호 검색에 따른 고객정보
	 * 역할 : 사용자가 입력한 전화번호를 받아서 해당하는 고객정보를 리턴
	 * 
	 */
	public CustomerVO selectByTel(String tel) throws Exception{
		CustomerVO dao = new CustomerVO();
		Connection con = null;
		PreparedStatement pr = null;
		//2. 연결객체 얻어오기
		try {
	    con = DriverManager.getConnection(URL,USER,PASS);
		//3. sql 문장만들기
		String sql = "SELECT*FROM MEMBER WHERE TEL = ?";
		//4 전송객체
		pr=con.prepareStatement(sql);
		pr.setString(1, tel);
		//5. 전송 - EXECUTEDQUERY OR EXECUTEUPDATE
		//결과를 CUSTMOMMERVO에 담기
		ResultSet rs = pr.executeQuery();
		if(rs.next()) {
			dao.setName(rs.getString("NAME"));
			dao.setAddr(rs.getString("ADDR"));
			dao.setTel(rs.getString("TEL"));
			dao.setAddtel(rs.getString("ADDTEL"));
			dao.setEmail(rs.getString("EMAIL"));
			
		}
		}finally {
		//6.닫기
			con.close();
			pr.close();
			
		}
		return dao;
		
	}
	
	public int updateCustomer(CustomerVO vo) throws Exception{
		
		int result = 0;
		
		
		return result;
	}

	
	public CustomerVO selectByName(String name) throws Exception {
		
		CustomerVO dao = new CustomerVO();
		Connection con = null;
		PreparedStatement pr = null;
		//2. 연결객체 얻어오기
		try {
	    con = DriverManager.getConnection(URL,USER,PASS);
		//3. sql 문장만들기
		String sql = "SELECT*FROM MEMBER WHERE NAME = ?";
		//4 전송객체
		pr=con.prepareStatement(sql);
		pr.setString(1, name);
		//5. 전송 - EXECUTEDQUERY OR EXECUTEUPDATE
		//결과를 CUSTMOMMERVO에 담기
		ResultSet rs = pr.executeQuery();
		if(rs.next()) {
			dao.setName(rs.getString("NAME"));
			dao.setAddr(rs.getString("ADDR"));
			dao.setTel(rs.getString("TEL"));
			dao.setAddtel(rs.getString("ADDTEL"));
			dao.setEmail(rs.getString("EMAIL"));
			
		}
		}finally {
		//6.닫기
			con.close();
			pr.close();
			
		}
		return dao;
		
	}
	public void selectByName() {
		
	}

	
	public void newinfo(CustomerVO vo) throws Exception {
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement pr = null;
		
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		
		
		// 3. sql 문장 만들기
		String sql = "UPDATE MEMBER SET name = ? ,addr = ? ,email = ? ,addtel = ? WHERE tel = ? ";
		
		// 4. sql 전송객체 (PreparedStatement)		
		
		pr = con.prepareStatement(sql);
		// 5. sql 전송
		pr.setString(1, vo.getName());
		pr.setString(2, vo.getAddr());
		pr.setString(3, vo.getEmail());
		pr.setString(4, vo.getAddtel());
		pr.setString(5, vo.getTel());
		pr.executeUpdate();
		
		}finally {
		// 6. 닫기 
			pr.close();
			con.close();
			
			
		}
		

	}
}