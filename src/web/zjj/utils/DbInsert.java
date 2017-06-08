package web.zjj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbInsert {

   public boolean isSuccessed = false;
   PreparedStatement pstmt = null;
//   ResultSet rs = null;
//   private Connection connection;
//   
//   public DbInsert(Connection connection){
//	   this.connection = connection;
//   }
   
   public DbInsert(Connection connection, String sql, Object...args) throws SQLException{
	   pstmt = (PreparedStatement) connection.prepareStatement(sql);
       for(int i=0; i< args.length; i++) {
           pstmt.setObject(i+1, args[i]);
       }
       //执行插入操作，executeUpdate方法返回成功的条数
       int num = pstmt.executeUpdate();
       if(num>0){
           isSuccessed = true;
       }
       
   }
   
	public void close() {
		if (pstmt != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
   
}
