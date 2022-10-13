package model;

import java.util.ArrayList;

public interface RentalDao {
	
	//대여
	public void rentVideo(String tel, int vnum) throws Exception;
	
	//반납
	public void retrunVideo(int vnum) throws Exception;
	
	
	//미납목록검색
	public ArrayList selectList() throws Exception;
	
	//전화번호로 조회
	public String  rentSelectTel(String tel) throws Exception;
	
	
	

}
