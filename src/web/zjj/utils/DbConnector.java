package web.zjj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnector {
    String driver = null;
    String url = null;
    String username = null;
    String password = null;
    private Connection connection;
    
    public DbConnector(String driver, String url, String username, String password){
    	this.driver = driver;
    	this.url = url;
    	this.username = username;
    	this.password = password;
    }

	// get database connection
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if(connection == null){
			Class.forName(this.driver);
			connection = DriverManager.getConnection(this.url, this.username, this.password);
		}

        return connection;
	} 
	
	public void closeConnection(){
        if(connection!=null){
            try{
                //关闭connectionection数据库连接对象
                connection.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
    
}
