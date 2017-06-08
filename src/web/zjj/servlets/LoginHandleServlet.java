package web.zjj.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import web.zjj.math.RegexUtils;
import web.zjj.utils.DbConnector;
import web.zjj.utils.DbDelete;
import web.zjj.utils.DbQuery;
import web.zjj.utils.JdbcUtils;

@WebServlet("/LoginHandleServlet")
public class LoginHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String errMsg;
    PrintWriter out; 
    int errorTimes = 0;
	
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		errMsg = "";//每次请求都注意请空消息，不然还会记录以前的值
		RequestDispatcher rd;
		//
		request.setCharacterEncoding("UTF-8");
	    //设置服务器端以UTF-8编码进行输出
	    response.setCharacterEncoding("UTF-8");
	    //设置浏览器以UTF-8编码进行接收,解决中文乱码问题
	    response.setContentType("text/html;charset=UTF-8"); 
		//必须在设置完response字符格式后才能获取PrintWriter对象,不然会出现乱码
	    out = response.getWriter(); 
	    
	    String jsonString = getJsonString(request);  
	    JSONObject jsonObject = null;
	    //如果有人直接输入loginhandle的地址，则跳转到登录页面，黑客有可能会这样做
	    try{
	    	jsonObject=JSONObject.fromObject(jsonString); 
	    } catch(Exception e){
	    	//web-inf下的文件不能通过重定向访问，重定向可以访问servlet
	    	response.sendRedirect(request.getContextPath()+"/login");
	    	return;
	    }
         
        String account = jsonObject.getString("account");
        String pass = jsonObject.getString("pass");
	    Connection conn = null;
	    DbQuery dbQuery = null;
	    
	    //
	    if(!checkSubmit(account, pass)){
	    	JSONObject obj = new JSONObject();
	    	try{
	    		obj.put("errMsg", errMsg);
	    	} catch(Exception e){
	    		e.printStackTrace();
	    	}
	        out.write(obj.toString());
	        out.flush();
	        out.close();
	    } else {
			// 既然不是null 就去两边的空格 其实我在jsp中已经清理掉空格了，这里是为了保险又加一遍,也可以不再重复处理
//	    	account = account.trim();
//	    	pass = pass.trim();
			try{
				conn = JdbcUtils.getConnection();
				dbQuery = new DbQuery(conn,
						"select uid,password,name from account_table where account = ?", account);
				ResultSet rs = dbQuery.rs;
				if (rs.next()) {
					// 用户名和密码匹配
					if (rs.getString("password").equals(pass)) {
						//清空错误次数统计数据
						//errorTimes = 0;
						// 获取session对象
						HttpSession session = request.getSession();
						session.setAttribute("name", rs.getString("name"));
						session.setAttribute("account", account);
						session.setAttribute("uid", rs.getString("uid"));
						// rd.foward()方法属于服务端跳转，客户端没有跳转，浏览器上后缀名在跳转之后仍是/loginservlet
						// 所以用resquest设置属性，不必使用session保存属性
//						request.setAttribute("account", account);
						// 获取转发对象
//						rd = request.getRequestDispatcher("/WEB-INF/NewFile.jsp");
						// 转发请求
//						rd.forward(request, response);
//						response.sendRedirect("http://www.baidu.com");
//						System.out.println("xxx");
//						return;
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
	    		
	    		JdbcUtils.close(conn);
			}
			
            //
			JSONObject obj2 = new JSONObject();
	    	try{
	    		obj2.put("errMsg", errMsg);
	    		obj2.put("account", account);
	    	} catch(Exception e){
	    		e.printStackTrace();
	    	}
	        out.write(obj2.toString());
	        out.flush();
	        out.close();
	    
	   }
	}
	
	private void dispatchErrorMessage(HttpServletRequest request, HttpServletResponse response){
		// 如果出错，转发到重新注册
		if (errMsg != null && !errMsg.equals("")) {
			RequestDispatcher rd = 
					request.getRequestDispatcher("/WEB-INF/login.jsp");
			request.setAttribute("err", errMsg);
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected String getJsonString(HttpServletRequest request){  
	    StringBuffer json=new StringBuffer();  
	    try {  
	        String line=null;  
	        BufferedReader reader = request.getReader();  
	        while((line=reader.readLine())!=null){  
	            json.append(line);  
	        }  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return json.toString();  
	} 
	
	private boolean checkSubmit(String account, String pass){
		if(RegexUtils.test("[^a-zA-Z0-9\\u4e00-\\u9fa5\\@\\.]", account)){
        	errMsg = "登录账号包含非法字符，请重新输入";
        	return false;
        }
        if(account.length() == 0 || account.equals("账号")){
        	errMsg = "登录账号不能为空";
        	return false;
        }
        if(RegexUtils.test("[^a-zA-Z0-9]", pass)){
        	errMsg = "密码应为字母数字的组合，请重新输入";
        	return false;
        }
        if(pass.length() == 0 || pass.equals("密码")){
        	errMsg = "密码不能为空";
        	return false;
        }
        
		return true;
	}
	
	private void setCookie(HttpServletRequest request, HttpServletResponse response){
        //获取浏览器访问访问服务器时传递过来的cookie数组
        Cookie[] cookies = request.getCookies();
        //如果用户是第一次出错，那么得到的cookies将是null
        if (cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("errorTimes")) {
                	int times = Integer.parseInt(cookie.getValue());
                	times += 1;
                	cookie.setValue(""+times);
                	response.addCookie(cookie);
                }
            }
        }else {
            //首次验证码出错时要设置一个整数值，存储到cookie中，然后发送到客户端浏览器
            Cookie cookie = new Cookie("errorTimes", "1");
            //cookie可以保存10分钟
            //cookie.setMaxAge(10*60);
            //将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
            response.addCookie(cookie);
        }
	}

}

