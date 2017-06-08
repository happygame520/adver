package web.zjj.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbQuery {

	//	boolean isSuccessed = false;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;
	public String sql2 = "";

	public DbQuery(Connection connection, String sql, Object... args) throws SQLException {
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			pstmt.setObject(i + 1, args[i]);
		}
		rs = pstmt.executeQuery();
		
	}
	
	//关闭顺序不要反了
	public void close() {
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        
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
