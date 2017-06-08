package web.zjj.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BgHandleServlet
 */
@WebServlet("/BgHandleServlet")
public class BgHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String errMsg;
    PrintWriter out; 
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		errMsg = "";
		request.setCharacterEncoding("UTF-8");
	    //设置服务器端以UTF-8编码进行输出
	    response.setCharacterEncoding("UTF-8");
	    //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
	    response.setContentType("text/html;charset=UTF-8"); 
		//必须在设置完response字符格式后才能获取PrintWriter对象,不然会出现乱码
	    out = response.getWriter(); 
	    
	    if(request.getParameter("flag").equals("exit")){
	    	HttpSession session = request.getSession(false);
	    	if(session != null){
//	    		session.removeAttribute("name");
//			    session.removeAttribute("account");
//			    session.removeAttribute("uid");
			    session.invalidate();
	    	} 
		    	
	    }
	    
	}


}
