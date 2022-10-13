package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.spi.DirStateFactory.Result;

import model.RentalDao;
import model.vo.CustomerVO;


public class RentDaoImpl implements RentalDao{//기본생성자
	//주소값설정
	final static String DRIVER    ="oracle.jdbc.driver.OracleDriver"; 
	final static String URL    = "jdbc:oracle:thin:@192.168.0.40:1521:xe";
	final static String USER    = "yu";
	final static String PASS    = "1234";
	
	//Connection con;
	
	public RentDaoImpl() throws Exception{
		// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버로딩 성공");
				
	}

	
	public void rentVideo(String tel, int vnum) throws Exception {
		//대여
		Connection con = null;
		PreparedStatement pr = null;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//SQL문장 
		String sql = "INSERT INTO rental (rental_number,tel,v_number,v_day,v_van)  "
				+ "  VALUES (seq_v_num.nextval, ?,?,sysdate,'N')";
		//전송객체
		pr = con.prepareStatement(sql);
		pr.setString(1, tel); //전화번호
		pr.setInt(2, vnum);  // 비디오번호
		//전송
		pr.executeUpdate();
		
		}finally {
			pr.close();
			con.close();
		}	
		
	}
	
	
	public void retrunVideo(int vnum) throws Exception {
		//반납
		Connection con = null;
		PreparedStatement pr = null;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//SQL 문장 (업데이트)
		String sql = "UPDATE RENTAL set v_van = 'Y' WHERE v_number = ? AND V_VAN = 'N'";
		//전송객체
		pr = con.prepareStatement(sql);
		pr.setInt(1, vnum);//비디오 번호
		//전송
		pr.executeUpdate();
		
		
		}finally {
			//닫기
			pr.close();
			con.close();
			
		}
	}

	
	public ArrayList selectList() throws Exception {
		//미납목록검색
		ArrayList data = new ArrayList();//객체 재정의
		Connection con = null;
		PreparedStatement pr = null;
		// 연결객체 얻어오기
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//sql 문장
		String sql =  "select v.v_number vnumber , v.v_name title , m.name name, m.tel tel, r.v_day+3 vanday, 'N' status "  
				+" from member m inner join  rental r"
				+" on M.tel = r.tel"
				+" inner join video v "
				+" on v.v_number = r.v_number "
				+" where r.v_van = 'N'";
		//전송객체
		pr = con.prepareStatement(sql);//preparedstatement를 드라이버에 대입
		//전송
		ResultSet rs =  pr.executeQuery();//resultset을 preparedstatement에 대입
		
		while(rs.next()){
			
			ArrayList temp = new ArrayList();
			temp.add(rs.getInt("vnumber"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("title"));//sql테이블에 있는 이름과 같아야함
		    temp.add(rs.getString("name"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("tel"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("vanday"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("status"));//sql테이블에 있는 이름과 같아야함
			data.add(temp);//한 덩어리로 묶는 문장
		}
		return data;
		
		}finally {
			pr.close(); //pr 닫기
			con.close();//드라이브 연결 닫기
		}
		
	}



	public String rentSelectTel(String tel) throws Exception {
		Connection con = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		String name = null; //이름을 받기위한 객체
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//sql 문장
		String sql = "SELECT name FROM member WHERE TEL = ? ";
		// 전송객체
		pr = con.prepareStatement(sql);
		pr.setString(1, tel);//전화번호
		//전송
		rs = pr.executeQuery();
		if(rs.next()) {
			name = rs.getString("NAME"); //sql의 컬럼명과 같아야 함
		}
		
		
		}finally {
			//닫기
			rs.close();
			pr.close();
			con.close();
			
		}return name;//이름을 출력하기 위한 리턴
		
	}//end of rentSelectTel
	
	
	
	
}
