import java.sql.*;

import javax.swing.JOptionPane;

public class DB{
	static Connection conn = null;
	public static Connection connectdb(){
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			conn=DriverManager.getConnection("jdbc:odbc:RGMSDB");
			
			return conn;

		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			
			return null;
		}
	}

}
