package web.zjj.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.zjj.utils.DbModify;
import web.zjj.utils.DbQuery;
import web.zjj.utils.JdbcUtils;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet("/PasswordServlet")
public class PasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String errMsg;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		errMsg = "";//每次请求都注意请空消息，不然还会记录以前的值
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		// 设置服务器端以UTF-8编码进行输出
		response.setCharacterEncoding("UTF-8");
		// 设置浏览器以UTF-8编码进行接收,解决中文乱码问题
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);// 防止创建新回话
		//
		String flag = request.getParameter("flag");
	    if(flag == null){
			//
		    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/code.jsp");
		  	rd.forward(request, response);
		  	return;
	    }
	    if(flag.equals("resetpassword")){
	    	String account = (String) session.getAttribute("account");
	    	String initcode = request.getParameter("initcode");
	    	String newcode1 = request.getParameter("newcode1");
	    	Connection conn = null;
		    DbQuery dbQuery = null;
		    DbModify dbModify = null;
	    	try{
				conn = JdbcUtils.getConnection();
				dbQuery = new DbQuery(conn,
						"select password from account_table where account = ?", account);
				ResultSet rs = dbQuery.rs;
				if (rs.next()) {
					// 用户名和密码匹配
					if (rs.getString("password").equals(initcode)) {
						String sql = "update account_table set password=? where account=?";
						dbModify = new DbModify(conn, sql, newcode1, account);
						if (dbModify.isSuccessed) {
							PrintWriter out = response.getWriter();
							out.write("success!");
							out.flush();
							out.close();
						}

					} else {
						errMsg += "您的账户密码不匹配，请重新输入";
					}
				} else {

					errMsg += "您的账户名不存在，请先注册";
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 
	    		if(dbQuery!=null)
	    			dbQuery.close();
	    		//
	    		if(dbModify!=null)
	    			dbModify.close();
	    		
	    		JdbcUtils.close(conn);
			}
	    	
	    	PrintWriter out = response.getWriter();
			out.write(errMsg);
			out.flush();
			out.close();

	    }
	    
	    
	}

}
