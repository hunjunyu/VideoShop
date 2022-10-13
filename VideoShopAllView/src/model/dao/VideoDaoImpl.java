package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.VideoDao;
import model.vo.VideoVO;

public class VideoDaoImpl implements VideoDao{
	
	final static String DRIVER    ="oracle.jdbc.driver.OracleDriver"; //오라클 드라이버
	final static String URL    = "jdbc:oracle:thin:@192.168.0.40:1521:xe";//컴퓨터안의 오라클주소
	final static String USER    = "yu";//계정 이름
	final static String PASS    = "1234";//계정 비밀번호

	
	public VideoDaoImpl() throws Exception{
		// 1. 드라이버로딩
		Class.forName(DRIVER);//드라이버 연결
		System.out.println("드라이버 로딩 성공");
	
	}
	
	//
	public void insertVideo(VideoVO vo, int count) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement pr = null;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);//드라이버와 sql연결
		// 3. sql 문장 만들기
		String sql = "INSERT INTO video(V_NUMBER,v_type,v_name,v_director,v_actor,v_exp)  "
				+ "  VALUES (seq_v_num.nextval, ?,?,?,?,?)";
		// 4. sql 전송객체 (PreparedStatement)	
		pr = con.prepareStatement(sql);
		pr.setString(1, vo.getV_type());
		pr.setString(2,vo.getV_Name());
		pr.setString(3, vo.getV_director());
		pr.setString(4, vo.getV_actor());
		pr.setString(5, vo.getV_exp());
		
		// 5. sql 전송
		for(int i = 0; i <count; i++) {
			pr.executeUpdate();			
		}
		
		}finally {
			pr.close();
			con.close();
		}
		
		
		// 6. 닫기
	}


	public ArrayList selectVideo() throws Exception {
		ArrayList data = new ArrayList();//객체 재정의
		Connection con = null;
		PreparedStatement pr = null;
		// 연결객체 얻어오기
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//sql 문장
		String sql = "SELECT V_NUMBER,V_NAME,V_DIRECTOR,V_ACTOR FROM video";
		//전송객체
		pr = con.prepareStatement(sql);//preparedstatement를 드라이버에 대입
		//전송
		ResultSet rs =  pr.executeQuery();//resultset을 preparedstatement에 대입
		while(rs.next()){
			
			ArrayList temp = new ArrayList();
			temp.add(rs.getInt("V_NUMBER"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("V_NAME"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("V_DIRECTOR"));//sql테이블에 있는 이름과 같아야함
			temp.add(rs.getString("V_ACTOR"));//sql테이블에 있는 이름과 같아야함
			data.add(temp);//한 덩어리로 묶는 문장
		}
		return data;
		
		}finally {
			pr.close(); //pr 닫기
			con.close();//드라이브 연결 닫기
		}
		
	}

	@Override
	public ArrayList selectVideo(int idx, String word) throws Exception {
		ArrayList data = new ArrayList();
		//2.연결객체 얻어오기
		Connection con = null;
		PreparedStatement pr = null;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		//3.SQL문장
		String [] colNames = {"v_name","v_director"};
		String sql = "SELECT v_number , v_name , v_director , v_actor FROM video "
				+ " WHERE "+ colNames[idx] +" LIKE '%"+ word +"%'";
		System.out.println(sql);
		//4.전송객체
		pr = con.prepareStatement(sql);
		//5. 전송
		ResultSet rs = pr.executeQuery();
		
		while(rs.next()) {
			ArrayList temp = new ArrayList();
			temp.add(rs.getInt("v_number"));
			temp.add(rs.getString("v_name"));
			temp.add(rs.getString("v_director"));
			temp.add(rs.getString("v_actor"));
			data.add(temp);	
			
		}
		return data;
		}finally {
			pr.close();
			con.close();
		}
		
	}
	/*
	 * 메소드명 : SelectByVnum
	 * 이자 : 비디오 번호
	 * 리턴값 : 비디오 정보
	 * 역할 : 비디오 번호를 넘겨받아 해당 비디오번호의 비디오정보를 리턴
	 * 
	 */
	
	public VideoVO  selectByVnum(int vnum)throws Exception {
		VideoVO vo = new VideoVO();
		Connection con = null;
		PreparedStatement pr = null;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		
		
		String sql = "SELECT*FROM VIDEO WHERE v_number = ? ";
		
		pr = con.prepareStatement(sql);
		pr.setInt(1, vnum);
		//5. 전송
		ResultSet rs = pr.executeQuery();
		
		if(rs.next()) {
			vo.setV_number(rs.getInt("v_number"));
			vo.setV_actor(rs.getString("v_actor"));
			vo.setV_director(rs.getString("v_director"));
			vo.setV_Name(rs.getString("v_name"));
			vo.setV_exp(rs.getString("v_exp"));
			vo.setV_type(rs.getString("v_type"));
		}
		
		
		return vo;
		}finally {
			pr.close();
			con.close();
		}
	
	
	}

	
	public int modifyVideo(VideoVO vo) throws Exception {
		Connection con = null;
		PreparedStatement pr = null;
		int a =0;
		try {
		con = DriverManager.getConnection(URL,USER,PASS);
		
		String sql = "UPDATE VIDEO SET v_exp = ? ,v_name = ? ,v_director = ? ,v_actor = ?, v_type = ?  WHERE V_NUMBER = ? ";
		
		pr = con.prepareStatement(sql);
		
		pr.setString(1, vo.getV_exp());
		pr.setString(2, vo.getV_Name());
		pr.setString(3, vo.getV_director());
		pr.setString(4, vo.getV_actor());
		pr.setString(5, vo.getV_type());
		
		pr.setInt(6, vo.getV_number());
	
		
		pr.executeUpdate();
		
		
		
		}finally {
			pr.close();
			con.close();
		}
		return a;
	}

	
	public int deleteVideo(int vnum) throws Exception {
		//연결 객체얻어오기
		Connection con = null;
		PreparedStatement pr = null;
		try {
		//연결 객체
		con = DriverManager.getConnection(URL,USER,PASS);
		//sql 구문
		String sql = "DELETE FROM VIDEO WHERE V_NUMBER = ? ";
		//전송 객체
        pr = con.prepareStatement(sql);
		pr.setInt(1, vnum);
        
		//전송
		pr.executeUpdate();
		
		
		}finally {
			//닫기
			pr.close();
			con.close();
			
		}
		return 0;
	}

	
	
	
	
	

}
