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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import web.zjj.utils.DbModify;
import web.zjj.utils.DbQuery;
import web.zjj.utils.JdbcUtils;

/**
 * Servlet implementation class ApplicationServlet
 */
@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		// 设置服务器端以UTF-8编码进行输出
		response.setCharacterEncoding("UTF-8");
		// 设置浏览器以UTF-8编码进行接收,解决中文乱码问题
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);//防止创建新回话
//	    if(session == null){
//	    	response.sendRedirect(request.getContextPath()+"/login");
//	    	return;
//	    }
//	    Object accountObj = session.getAttribute("account");
//	    if(accountObj == null){
//	    	response.sendRedirect(request.getContextPath()+"/login");
//	    	return;
//	    }
		//
	    String flag = request.getParameter("flag");
	    if(flag == null){
			//
		    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/application.jsp");
		  	rd.forward(request, response);
		  	return;
	    }
		if (flag.equals("load")) {
			//
			String uid = (String) session.getAttribute("uid");
			String tablename = uid + "_app_table";
			Connection conn = null;
			DbQuery dbQuery = null;
			try {
				conn = JdbcUtils.getConnection();
				dbQuery = new DbQuery(conn, "select * from " + tablename);
				ResultSet rs = dbQuery.rs;
				JSONArray array = new JSONArray();
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					obj.put("appname", rs.getString("appname"));
					obj.put("uid", uid);
					obj.put("appid", rs.getString("appid"));
					obj.put("appkey", rs.getString("appkey"));
					obj.put("appstatus", rs.getString("appstatus").trim());

					array.add(obj);
				}
				PrintWriter out = response.getWriter();  
		        out.write(array.toString()); 
		        out.flush();
		        out.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//
				if (dbQuery != null)
					dbQuery.close();

				JdbcUtils.close(conn);
			}
		}
		//
		if (flag.equals("set")) {
		    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/appset.jsp");
		  	rd.forward(request, response);
		  	return;
		}
		if(flag.equals("save")){
			String appstatus2 = request.getParameter("appstatus2");// 从修改页面传来的数值
			String uid = request.getParameter("uid").trim();
			String appid = request.getParameter("appid").trim();
			String tablename = uid + "_app_table";
			String sql = "update " + tablename + " set appstatus=?" + " where appid=" + appid;
			Connection conn = null;
			DbModify dbModify = null;
			try {
				conn = JdbcUtils.getConnection();
				dbModify = new DbModify(conn, sql, appstatus2);
				if (dbModify.isSuccessed) {
					PrintWriter out = response.getWriter();
					out.write("success!");
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//
				if (dbModify != null)
					dbModify.close();

				JdbcUtils.close(conn);
			}
		}
	   
		
	}

}
