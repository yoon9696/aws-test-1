package bbs;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class BbsDAO {

	private Connection conn;//Connection데이터베이스에 접근하기위한 객체의미
	private ResultSet rs;//정보를담을수있는 하나의 객체
	
	public BbsDAO() {//생성자
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbID="oracledb";
			String dbPassword="wwss88";
			Class.forName("oracle.jdbc.driver.OracleDriver");//접속할수있도록 하는 하나의 라이브러리(Driver)
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//여기까지가 실제 오라클에 접속을 할수있는 부분을 입력한거임.
	
	public Date getDate() {
		String SQL = "select sysdate from dual";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDate(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null; //데이터베이스오류
	}
	public int getNext() {
		String SQL = "SELECT bbsID from BBS1 order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1;// 첫번째 개시글인 경우
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스오류
	}
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "insert into BBS1(bbsID,bbsTitle,userID,bbsDate,bbsContent,bbsAvailable) values(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setDate(4, (java.sql.Date) getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6,1);
		
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스오류
	
	}
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * from BBS1 where rownum <=10 AND bbsAvailable = 1 order by bbsID desc";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setInt(1, getNext() -(pageNumber -1) * 10);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			Bbs bbs = new Bbs();
			bbs.setBbsID(rs.getInt(1));//bbs 안에 데이터를 넣는다
			bbs.setBbsTitle(rs.getString(2));
			bbs.setUserID(rs.getString(3));
			bbs.setBbsDate(rs.getString(4));
			bbs.setBbsContent(rs.getString(5));
			bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);//결과적으로 데이터들을 반환(리스트로)할수있도록 넣는다.
				}
							
					}catch(Exception e) {
						e.printStackTrace();
					}
					return list; 
				}
			//	public boolean nextPage(int pageNumber) {//페이징 처리를 위해 존재하는 함수
				//	String SQL = "SELECT * from BBS1 where rownum <=10 AND bbsAvailable = 1 order by bbsID desc";
				//	try {
				//	PreparedStatement pstmt = conn.prepareStatement(SQL);
				//	pstmt.setInt(1, getNext() -(pageNumber -1) * 10);
				//	rs = pstmt.executeQuery();
					//if(rs.next()) {
				//		return true;
				//	}
			
				//	}catch(Exception e) {
				//	e.printStackTrace();
				//	}
				//		return false; 
				}
	

	

