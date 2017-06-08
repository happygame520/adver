package web.zjj.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		//
		request.setCharacterEncoding("UTF-8");
	    //设置服务器端以UTF-8编码进行输出
	    response.setCharacterEncoding("UTF-8");
	    //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
	    response.setContentType("text/html;charset=UTF-8"); 
		//必须在设置完response字符格式后才能获取PrintWriter对象,不然会出现乱码
	    
	    //首次登录需要处理
		rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}

}
