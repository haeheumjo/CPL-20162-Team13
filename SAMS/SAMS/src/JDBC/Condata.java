package JDBC;


import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
public class Condata{
	private static Connection con;
	ResultSet rs;
	private static Context ctx;
	private static Context env;
	public static synchronized Connection getConnection() throws Exception{
		try{
			ctx=new InitialContext();
			env=(Context)ctx.lookup("java:comp/env");
			DataSource ds=(DataSource)env.lookup("jdbc/sa");
			return con=ds.getConnection();
		}
		catch(SQLException e){
			throw e;
		}
		catch(NamingException e){
			throw e;
		}

	}
	public ResultSet executeQuery(String sql){
		try{
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery(sql);
		}catch(SQLException er){
			System.err.print(er.getMessage());
		}
			return rs;
		}
	public int executeUpdate(String sql){
		int result=0;
		try{
			Statement stmt=con.createStatement();
			result=stmt.executeUpdate(sql); 
		}catch(SQLException eer){
			System.out.print(eer.getMessage());
		}
		return result;
	}
	public void close(){
		try{
			if(con!=null){
				con.close();
			}
		}catch(Exception err){
			System.out.print(err);
		}
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(Exception err){
			System.out.print(err);
		}
	}
}