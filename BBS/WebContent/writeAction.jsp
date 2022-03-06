<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bbs.BbsDAO" %>
<%@page import="java.io.PrintWriter" %>
<%request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/>
<jsp:setProperty name="bbs" property="bbsTitle"/>
<jsp:setProperty name="bbs" property="bbsContent"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
	String userID = null;
	if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
}
	if(userID == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을하세요')");
		script.println("location.href = 'login.jsp'");//다시로그인페이지로 돌려보냄.
		script.println("</script>");
	}else{
		if(bbs.getBbsTitle() == null || bbs.getBbsContent() ==null){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('입력이 안 된 사항이 있습니다.')");
					script.println("history.back()");//다시로그인페이지로 돌려보냄.
					script.println("</script>");
				}else{
					BbsDAO bbsDAO = new BbsDAO();//인스턴스생성.
					int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());//다 받아야되니 userDAO.join(user)
					//로그인페이지에서 유저아디이,유저패스워드 입력된 값으로 넘어온다.
					if(result ==-1){
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('글쓰기 실패')");
						script.println("history.back()");//다시로그인페이지로 돌려보냄.
						script.println("</script>");
					}
					else  {
						
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("location.href='bbs.jsp'");
						script.println("</script>");
					}
				}
	}
		

	%>
</body>
</html>