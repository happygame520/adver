package web.zjj.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BgServlet")
public class BgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    //设置服务器端以UTF-8编码进行输出
	    response.setCharacterEncoding("UTF-8");
	    //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
	    response.setContentType("text/html;charset=UTF-8"); 
	    HttpSession session = request.getSession(false);//防止创建新回话
	    if(session == null){
	    	response.sendRedirect(request.getContextPath()+"/login");
	    	return;
	    }
	    Object accountObj = session.getAttribute("account");
	    if(accountObj == null){
	    	response.sendRedirect(request.getContextPath()+"/login");
	    	return;
	    }
//	    String account = (String)accountObj;
//	    System.out.println("acc="+account);
		//必须在设置完response字符格式后才能获取PrintWriter对象,不然会出现乱码
//	    PrintWriter out = response.getWriter();
//	    out.println("hello: "+account);
//	    out.flush();
//	    out.close();
	    
	    //首次登录需要处理
	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/main.jsp");
	  	rd.forward(request, response);
		
	}

}
