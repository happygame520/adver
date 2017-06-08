package web.zjj.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import web.zjj.utils.DbQuery;
import web.zjj.utils.JdbcUtils;

/**
 * Servlet implementation class RechargeHistoryServlet
 */
@WebServlet("/RechargeHistoryServlet")
public class RechargeHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		// 设置服务器端以UTF-8编码进行输出
		response.setCharacterEncoding("UTF-8");
		// 设置浏览器以UTF-8编码进行接收,解决中文乱码问题
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(false);//防止创建新回话
        //
	    String flag = request.getParameter("flag");
	    if(flag == null){
	    	String date_start = request.getParameter("date_start");
	    	String date_end = request.getParameter("date_end");
	    	if(date_start == null || date_end == null){
	    		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    		request.setAttribute("date_end", date);
	    		//取前一月数据
	    		Calendar c = Calendar.getInstance();
	            c.setTime(new Date());
	            c.add(Calendar.MONTH, -1);
	            String mon = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    		request.setAttribute("date_start", mon);
		    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/rechargehistory.jsp");
			  	rd.forward(request, response);
			  	return;
	    	} else {
	    		request.setAttribute("date_start", date_start);
	    		request.setAttribute("date_end", date_end);
		    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/rechargehistory.jsp");
			  	rd.forward(request, response);
			  	return;
	    	}
	    }
	    if (flag.equals("queryrechargehistory")) {
	    	String date_start = request.getParameter("date_start");
	    	String date_end = request.getParameter("date_end");
			String uid = (String) session.getAttribute("uid");
			String tablename = uid + "_rechargehistory_table";
			Connection conn = null;
			ArrayList<DbQuery> list = new ArrayList<DbQuery>();
			JSONArray jsonArray = new JSONArray();
			try {
				conn = JdbcUtils.getConnection();
				DbQuery dbQuery = new DbQuery(conn, "select * from " + tablename + 
						" where date>=? and date<=? ", date_start, date_end);
				list.add(dbQuery);
				ResultSet rs = dbQuery.rs;
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					obj.put("date", rs.getString("date"));
					obj.put("amount", rs.getFloat("amount"));
					obj.put("status", rs.getString("status"));
                     
					jsonArray.add(obj);
				}
				Collections.reverse(jsonArray);//数组翻转
				PrintWriter out = response.getWriter();  
		        out.write(jsonArray.toString()); 
		        out.flush();
		        out.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//
				Iterator<DbQuery> iter = list.iterator();
				while(iter.hasNext()){
					DbQuery dbQuery = iter.next();
					if(dbQuery != null){
						dbQuery.close();
					}
				}

				JdbcUtils.close(conn);
			}
		}
		
	}

}
