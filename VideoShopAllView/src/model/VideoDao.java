package model;

import java.util.ArrayList;

import model.vo.VideoVO;

public interface VideoDao {
	//비디오 입고
	public void 		insertVideo(VideoVO vo, int count) throws Exception;
	// 비디오 검색
	public ArrayList    selectVideo(int idx, String word) throws Exception;
	// 비디오 VO
	public VideoVO  	selectByVnum(int vnum)throws Exception;
	// 비디오 수정
	public  int    modifyVideo(VideoVO vo)throws Exception;
	// 비디오 삭제
	public  int  deleteVideo(int vnum)throws Exception;
	
}
