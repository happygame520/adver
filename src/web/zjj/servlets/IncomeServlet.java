package web.zjj.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import web.zjj.math.PriceUtils;
import web.zjj.utils.DbQuery;
import web.zjj.utils.JdbcUtils;

/**
 * Servlet implementation class IncomeServlet
 */
@WebServlet("/IncomeServlet")
public class IncomeServlet extends HttpServlet {
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
	    		request.setAttribute("date_start", date);
	    		request.setAttribute("date_end", date);
		    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/income.jsp");
			  	rd.forward(request, response);
			  	return;
	    	} else {
	    		request.setAttribute("date_start", date_start);
	    		request.setAttribute("date_end", date_end);
		    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/frame/income.jsp");
			  	rd.forward(request, response);
			  	return;
	    	}
	    }
	    if (flag.equals("queryincome")) {
			//
	    	String date_start = request.getParameter("date_start");
	    	String date_end = request.getParameter("date_end");
			String uid = (String) session.getAttribute("uid");
			String tablename = uid + "_app_table";
			Connection conn = null;
			ArrayList<DbQuery> list = new ArrayList<DbQuery>();
			JSONArray jsonArray = new JSONArray();
			try {
				conn = JdbcUtils.getConnection();
				DbQuery dbQuery = new DbQuery(conn, "select * from " + tablename);
				list.add(dbQuery);
				ResultSet rs = dbQuery.rs;
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					String tablename2 = uid + "_" + rs.getString("appid") + "_data_table";
					DbQuery dbQuery2 = new DbQuery(conn, "select * from " + tablename2 + 
							" where date>=? and date<=? ", date_start, date_end);
					list.add(dbQuery2);
					ResultSet rs2 = dbQuery2.rs;
					int cpaNumber = 0;
					while(rs2.next()){
						cpaNumber += rs2.getInt("install_opt");
//						System.out.println("time=" + rs2.getInt("install_opt"));
					}
					obj.put("cpa", cpaNumber);
					obj.put("appname", rs.getString("appname"));
					obj.put("cost", cpaNumber*rs.getFloat("unitprice"));

					jsonArray.add(obj);
				}
				//
//				Iterator<JSONObject> iter = jsonArray.iterator();
//				while (iter.hasNext()) {
//					JSONObject obj = iter.next();
//				}
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
