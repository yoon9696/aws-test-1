package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;//Connection데이터베이스에 접근하기위한 객체의미
	private PreparedStatement pstmt;
	private ResultSet rs;//정보를담을수있는 하나의 객체
	
	public UserDAO() {//생성자
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
	public int login(String userID, String userPassword) {//실제로 로그인을 시도하는 하나의 함수를 만든다. 유저아이디와 패스워드를 받아서 처리할수있게
		String SQL = "SELECT userPassword from BBS where userID=?";//BBS테이블에서 해당 사용자의 비밀번호를 가져올수있게 하는것.
	try {
		pstmt = conn.prepareStatement(SQL);//어떠한 정해진 SQL문장을 데이터베이스에 삽입하는 그러한 형식으로 가져온다.
		pstmt.setString(1, userID);//(위처럼 하나의 문장을 미리준비 중간에 ?넣어놓았다가 나중에 그 ?에 해당하는 내용을 USERID를 넣어준거)
									//즉 매개변수로 넘어온 유절아이디를 ?에들어갈수 있도록해주어서 실제로 db에는 접속을 시도하고자하는 사용자의 id를 입력받아서
									//그 아이디가 실제로 존재하는지 확인.존재한다면 비밀번호는 무엇인지 가져올수 있도록한다.
		rs = pstmt.executeQuery();//결과를 담을수 있는 하나의 객체에 실행한 결과를 넣어준다.
		if(rs.next()) {//결과가 존재한다면 (1실행 , 그렇지 않다면0)
			if(rs.getString(1).equals(userPassword)) {//아이디가있는경우(결과로나온 유저패스워드를받아서) 동일하다면
				return 1;//로그인성공 즉 쿼리문실행해서나온결과와 입력한결과가 같으면 
			}
			else
				return 0;//비밀번호틈림.
		}
		return-1;//아이디가없다.
	}catch(Exception e) {
		e.printStackTrace();
	}
	return -2;//데이터베이스 오류
	}
	
	public int join(User user) {//한명의 사용자 입력받을수 있도록한다.
		String SQL = "insert into BBS values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());//각각?해당하는거 넣을수있도록한다.
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();//실행한 결과.(성공)
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//데이터베이스 오류(동일이아이디)
	}
}